package com.supcoder.devkit.vm

import androidx.compose.material3.SnackbarDuration
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.apksig.ApkSigner
import com.android.apksig.ApkVerifier
import com.android.apksig.KeyConfig
import com.android.ide.common.signing.KeystoreHelper
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.coroutines.FlowSettings
import com.supcoder.devkit.database.PreferencesDataSource
import com.supcoder.devkit.model.ApkInformation
import com.supcoder.devkit.model.ApkSignature
import com.supcoder.devkit.model.ThemeConfig
import com.supcoder.devkit.model.IconFactoryInfo
import com.supcoder.devkit.model.JunkCodeInfo
import com.supcoder.devkit.model.KeyStoreInfo
import com.supcoder.devkit.model.SignaturePolicy
import com.supcoder.devkit.model.SnackbarVisualsData
import com.supcoder.devkit.model.UserData
import com.supcoder.devkit.model.Verifier
import com.supcoder.devkit.model.VerifierResult
import com.supcoder.devkit.router.Page
import com.supcoder.devkit.util.WhileUiSubscribed
import com.supcoder.devkit.util.browseFileDirectory
import com.supcoder.devkit.util.extractIcon
import com.supcoder.devkit.util.extractValue
import com.supcoder.devkit.util.extractVersion
import com.supcoder.devkit.util.getVerifier
import com.supcoder.devkit.util.isMac
import com.supcoder.devkit.util.isWindows
import com.supcoder.devkit.util.resourcesDirWithOs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.apache.commons.codec.digest.DigestUtils
import utils.update
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.InputStream
import java.io.InputStreamReader
import java.security.KeyStore
import java.security.cert.X509Certificate

/**
 * @Author      : LazyIonEs
 * @CreateDate  : 2024/1/31 14:45
 * @Description : MainViewModel
 * @Version     : 1.0
 */
class MainViewModel @OptIn(ExperimentalSettingsApi::class) constructor(settings: FlowSettings) : ViewModel() {

    // 数据存储
    @OptIn(ExperimentalSettingsApi::class)
    private val preferences = PreferencesDataSource(settings)

    // 偏好设置
    val themeConfig = preferences.themeConfig.stateIn(
        scope = viewModelScope, started = WhileUiSubscribed, initialValue = PreferencesDataSource.DEFAULT_THEME_CONFIG
    )



    // 偏好设置
    val userData = preferences.userData.stateIn(
        scope = viewModelScope, started = WhileUiSubscribed, initialValue = PreferencesDataSource.DEFAULT_USER_DATA
    )



    // 主页选中下标
    private val _uiPageIndex = mutableStateOf(Page.APK_DECOMPILE)
    val uiPageIndex by _uiPageIndex

    fun updateUiState(page: Page) {
        _uiPageIndex.update { page }
    }

    // 签名信息UI状态
    private val _verifierState = mutableStateOf<UIState>(UIState.WAIT)
    val verifierState by _verifierState

    // APK签名信息
    private val _apkSignatureState = mutableStateOf(ApkSignature())
    val apkSignatureState by _apkSignatureState

    // Apk签名UI状态
    private val _apkSignatureUIState = mutableStateOf<UIState>(UIState.WAIT)
    val apkSignatureUIState by _apkSignatureUIState

    // 签名生成信息
    private val _keyStoreInfoState = mutableStateOf(KeyStoreInfo())
    val keyStoreInfoState by _keyStoreInfoState

    // 签名生成UI状态
    private val _keyStoreInfoUIState = mutableStateOf<UIState>(UIState.WAIT)
    val keyStoreInfoUIState by _keyStoreInfoUIState

    // Apk信息UI状态
    private val _apkInformationState = mutableStateOf<UIState>(UIState.WAIT)
    val apkInformationState by _apkInformationState

    // 垃圾代码生成信息
    private val _junkCodeInfoState = mutableStateOf(JunkCodeInfo())
    val junkCodeInfoState by _junkCodeInfoState

    // 垃圾代码生成UI状态
    private val _junkCodeUIState = mutableStateOf<UIState>(UIState.WAIT)
    val junkCodeUIState by _junkCodeUIState

    // 图标工厂信息
    private val _iconFactoryInfoState = mutableStateOf(IconFactoryInfo())
    val iconFactoryInfoState by _iconFactoryInfoState



    private val _snackbarVisuals = MutableStateFlow(SnackbarVisualsData())
    val snackbarVisuals = _snackbarVisuals.asStateFlow()

    fun toggleDarkMode(isDarkMode :Boolean){
        saveThemeConfig(if (isDarkMode) ThemeConfig.DARK else ThemeConfig.LIGHT)
    }

    /**
     * 更新主题
     */
    fun saveThemeConfig(themeConfig: ThemeConfig) {
        viewModelScope.launch {
            preferences.saveThemeConfig(themeConfig)
        }
    }

    /**
     * 更新用户偏好
     */
    fun saveUserData(userData: UserData) {
        viewModelScope.launch {
            preferences.saveUserData(userData)
        }
    }



    /**
     * 显示快捷信息栏
     * @param value SnackbarVisualsData
     * @see model.SnackbarVisualsData
     */
    private fun updateSnackbarVisuals(value: SnackbarVisualsData) {
        _snackbarVisuals.update { value }
    }

    /**
     * 显示快捷信息栏
     */
    fun updateSnackbarVisuals(value: String) {
        _snackbarVisuals.update { currentState ->
            currentState.copy(message = value).reset()
        }
    }

    /**
     * 修改ApkSignature
     * @param apkSignature ApkSignature
     * @see model.ApkSignature
     */
    fun updateApkSignature(apkSignature: ApkSignature) {
        _apkSignatureState.update { apkSignature }
    }

    /**
     * 修改SignatureGenerate
     * @param keyStoreInfo KeyStoreInfo
     * @see model.KeyStoreInfo
     */
    fun updateSignatureGenerate(keyStoreInfo: KeyStoreInfo) {
        _keyStoreInfoState.update { keyStoreInfo }
    }



    /**
     * APK签名
     */
    fun apkSigner() = viewModelScope.launch(Dispatchers.IO) {
        try {
            val signerSuffix = userData.value.defaultSignerSuffix
            val flagDelete = userData.value.duplicateFileRemoval
            val isAlignFileSize = userData.value.alignFileSize
            _apkSignatureUIState.update { UIState.Loading }
            val inputApk = File(apkSignatureState.apkPath)
            val outputApk = File(
                apkSignatureState.outputPath, "${inputApk.nameWithoutExtension}${signerSuffix}.apk"
            )
            if (outputApk.exists()) {
                if (flagDelete) {
                    outputApk.delete()
                } else {
                    throw Exception("输出文件已存在：${outputApk.name}")
                }
            }
            val key = File(apkSignatureState.keyStorePath)
            val v1SigningEnabled =
                apkSignatureState.keyStorePolicy == SignaturePolicy.V1 || apkSignatureState.keyStorePolicy == SignaturePolicy.V2 || apkSignatureState.keyStorePolicy == SignaturePolicy.V3
            val v2SigningEnabled =
                apkSignatureState.keyStorePolicy == SignaturePolicy.V2 || apkSignatureState.keyStorePolicy == SignaturePolicy.V2Only || apkSignatureState.keyStorePolicy == SignaturePolicy.V3
            val v3SigningEnabled = apkSignatureState.keyStorePolicy == SignaturePolicy.V3
            val alisa = apkSignatureState.keyStoreAlisaList?.getOrNull(apkSignatureState.keyStoreAlisaIndex)
            val certificateInfo = KeystoreHelper.getCertificateInfo(
                "JKS", key, apkSignatureState.keyStorePassword, apkSignatureState.keyStoreAlisaPassword, alisa
            )
            val privateKey = certificateInfo.key
            val certificate = certificateInfo.certificate
            val keyConfig = KeyConfig.Jca(privateKey)
            val signerConfig = ApkSigner.SignerConfig.Builder("CERT", keyConfig, listOf(certificate)).build()
            val signerBuild = ApkSigner.Builder(listOf(signerConfig))
            val apkSigner = signerBuild.setInputApk(inputApk).setOutputApk(outputApk).setAlignFileSize(isAlignFileSize)
                .setV1SigningEnabled(v1SigningEnabled).setV2SigningEnabled(v2SigningEnabled)
                .setV3SigningEnabled(v3SigningEnabled).setAlignmentPreserved(!isAlignFileSize).build()
            apkSigner.sign()
            val snackbarVisualsData = SnackbarVisualsData(message = "APK签名成功，点击跳转至已签名文件",
                actionLabel = "跳转",
                withDismissAction = true,
                duration = SnackbarDuration.Short,
                action = {
                    browseFileDirectory(outputApk)
                })
            updateSnackbarVisuals(snackbarVisualsData)
        } catch (e: Exception) {
            e.printStackTrace()
            updateSnackbarVisuals(e.message ?: "签名失败，请联系开发者排查问题")
        }
        _apkSignatureUIState.update { UIState.WAIT }
    }

    /**
     * APK信息
     * @param input 输入APK路径
     */
    fun apkInformation(input: String) = viewModelScope.launch(Dispatchers.IO) {
        var process: Process? = null
        var inputStream: InputStream? = null
        var bufferedReader: BufferedReader? = null
        try {
            val aapt = File(
                resourcesDirWithOs, if (isWindows) {
                    "aapt2.exe"
                } else if (isMac) {
                    "aapt2"
                } else {
                    "aapt2"
                }
            )
            if (!aapt.canExecute()) {
                aapt.setExecutable(true)
            }
            _apkInformationState.update { UIState.Loading }
            val builder = ProcessBuilder()
            process = builder.command(aapt.absolutePath, "dump", "badging", input).start()
            inputStream = process!!.inputStream

            var errors = ""

            viewModelScope.launch(Dispatchers.IO) {
                process.errorStream.use { stream ->
                    BufferedReader(InputStreamReader(stream, "utf-8")).use { reader ->
                        reader.readLines().forEach {
                            errors += it
                        }
                    }
                }
            }

            bufferedReader = BufferedReader(InputStreamReader(inputStream!!, "utf-8"))
            var line: String?
            val apkInformation = ApkInformation()
            val apkFile = File(input)
            apkInformation.size = apkFile.length()
            apkInformation.md5 = DigestUtils.md5Hex(FileInputStream(apkFile))
            while (bufferedReader.readLine().also { line = it } != null) {
                line?.let {
                    if (it.startsWith("application-icon-640:")) {
                        val path = (it.split("application-icon-640:").getOrNull(1) ?: "").trim().replace("'", "")
                        apkInformation.icon = extractIcon(input, path)
                    } else if (it.startsWith("application:")) {
                        apkInformation.label = extractValue(it, "label")
                        if (apkInformation.icon == null) {
                            apkInformation.icon = extractIcon(input, extractValue(it, "icon"))
                        } else {

                        }
                    } else if (it.startsWith("package:")) {
                        apkInformation.packageName = extractValue(it, "name")
                        apkInformation.versionCode = extractValue(it, "versionCode")
                        apkInformation.versionName = extractValue(it, "versionName")
                        apkInformation.compileSdkVersion = extractValue(it, "compileSdkVersion")
                    } else if (it.startsWith("targetSdkVersion:")) {
                        apkInformation.targetSdkVersion = extractVersion(it, "targetSdkVersion")
                    } else if (it.startsWith("sdkVersion:")) {
                        apkInformation.minSdkVersion = extractVersion(it, "sdkVersion")
                    } else if (it.startsWith("uses-permission:")) {
                        if (apkInformation.usesPermissionList == null) {
                            apkInformation.usesPermissionList = ArrayList()
                        }
                        apkInformation.usesPermissionList?.add(extractValue(it, "name"))
                    } else if (it.startsWith("native-code:")) {
                        apkInformation.nativeCode =
                            (it.split("native-code:").getOrNull(1) ?: "").trim().replace("'", "")
                    } else {

                    }
                }
            }
            if (apkInformation.isBlank()) {
                updateSnackbarVisuals(errors)
                _apkInformationState.update { UIState.WAIT }
            } else {
                _apkInformationState.update { UIState.Success(apkInformation) }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            updateSnackbarVisuals(e.message ?: "APK解析失败")
            _apkInformationState.update { UIState.WAIT }
        } finally {
            process?.destroy()
            inputStream?.close()
            bufferedReader?.close()
        }
    }

    /**
     * 生成签名
     */
    fun createSignature() = viewModelScope.launch(Dispatchers.IO) {
        try {
            val destStoreType = userData.value.destStoreType
            val destStoreSize = userData.value.destStoreSize.size
            _keyStoreInfoUIState.update { UIState.Loading }
            val outputFile = File(keyStoreInfoState.keyStorePath, keyStoreInfoState.keyStoreName)
            val result = KeystoreHelper.createNewStore(
                destStoreType.name,
                outputFile,
                keyStoreInfoState.keyStorePassword,
                keyStoreInfoState.keyStoreAlisaPassword,
                keyStoreInfoState.keyStoreAlisa,
                "CN=${keyStoreInfoState.authorName},OU=${keyStoreInfoState.organizationalUnit},O=${keyStoreInfoState.organizational},L=${keyStoreInfoState.city},S=${keyStoreInfoState.province}, C=${keyStoreInfoState.countryCode}",
                keyStoreInfoState.validityPeriod.toInt(),
                destStoreSize
            )
            if (result) {
                val snackbarVisualsData = SnackbarVisualsData(message = "创建签名成功，点击跳转至签名文件",
                    actionLabel = "跳转",
                    withDismissAction = true,
                    duration = SnackbarDuration.Short,
                    action = {
                        browseFileDirectory(outputFile)
                    })
                updateSnackbarVisuals(snackbarVisualsData)
            } else {
                updateSnackbarVisuals("签名制作失败，请检查输入项是否合法。")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            updateSnackbarVisuals(e.message ?: "签名制作失败，请检查输入项是否合法。")
        }
        _keyStoreInfoUIState.update { UIState.WAIT }
    }

    /**
     * 签名信息
     * @param input 输入签名的路径
     * @param password 签名密码
     * @param alisa 签名别名
     */
    fun signerVerifier(input: String, password: String, alisa: String) = viewModelScope.launch(Dispatchers.IO) {
        _verifierState.update { UIState.Loading }
        var fileInputStream: FileInputStream? = null
        val inputFile = File(input)
        try {
            val list = ArrayList<Verifier>()
            val keyStore = KeyStore.getInstance(KeyStore.getDefaultType())
            fileInputStream = FileInputStream(inputFile)
            keyStore.load(fileInputStream, password.toCharArray())
            val cert = keyStore.getCertificate(alisa)
            if (cert.type == "X.509") {
                cert as X509Certificate
                list.add(cert.getVerifier(cert.version))
                val apkVerifierResult = VerifierResult(
                    isSuccess = true, isApk = false, path = input, name = inputFile.name, data = list
                )
                _verifierState.update { UIState.Success(apkVerifierResult) }
            } else {
                throw Exception("Key Certificate Type Is Not X509Certificate")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            updateSnackbarVisuals(e.message ?: "签名验证失败")
            _verifierState.update { UIState.WAIT }
        } finally {
            fileInputStream?.close()
        }
    }

    /**
     * APK签名信息
     * @param input 输入APK的路径
     */
    fun apkVerifier(input: String) = viewModelScope.launch(Dispatchers.IO) {
        _verifierState.update { UIState.Loading }
        val list = ArrayList<Verifier>()
        val inputFile = File(input)
        val path = inputFile.path
        val name = inputFile.name
        val verifier: ApkVerifier = ApkVerifier.Builder(inputFile).build()
        try {
            val result = verifier.verify()
            var error = ""
            val isSuccess = result.isVerified

            result.errors.filter { it.issue == ApkVerifier.Issue.JAR_SIG_UNPROTECTED_ZIP_ENTRY }.forEach {
                error += it.toString() + "\n"
            }

            if (result.v1SchemeSigners.isNotEmpty()) {
                for (signer in result.v1SchemeSigners) {
                    val cert = signer.certificate ?: continue
                    if (signer.certificate.type == "X.509") {
                        list.add(cert.getVerifier(1))
                    }
                    signer.errors.filter { it.issue == ApkVerifier.Issue.JAR_SIG_UNPROTECTED_ZIP_ENTRY }.forEach {
                        error += it.toString() + "\n"
                    }
                }
            }

            if (result.v2SchemeSigners.isNotEmpty()) {
                for (signer in result.v2SchemeSigners) {
                    val cert = signer.certificate ?: continue
                    if (signer.certificate.type == "X.509") {
                        list.add(cert.getVerifier(2))
                    }
                    signer.errors.filter { it.issue == ApkVerifier.Issue.JAR_SIG_UNPROTECTED_ZIP_ENTRY }.forEach {
                        error += it.toString() + "\n"
                    }
                }
            }

            if (result.v3SchemeSigners.isNotEmpty()) {
                for (signer in result.v3SchemeSigners) {
                    val cert = signer.certificate ?: continue
                    if (signer.certificate.type == "X.509") {
                        list.add(cert.getVerifier(3))
                    }
                    signer.errors.filter { it.issue == ApkVerifier.Issue.JAR_SIG_UNPROTECTED_ZIP_ENTRY }.forEach {
                        error += it.toString() + "\n"
                    }
                }
            }

            if (isSuccess || list.isNotEmpty()) {
                val apkVerifierResult = VerifierResult(isSuccess, true, path, name, list)
                _verifierState.update { UIState.Success(apkVerifierResult) }
            } else {
                if (error.isBlank()) {
                    error = "APK签名验证失败"
                }
                updateSnackbarVisuals(error)
                _verifierState.update { UIState.WAIT }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            updateSnackbarVisuals(e.message ?: "APK签名验证失败")
            _verifierState.update { UIState.WAIT }
        }
    }

    /**
     * 生成垃圾代码 aar
     */




    /**
     * 验证签名
     * @param path 签名路径
     * @param password 签名密码
     */
    fun verifyAlisa(path: String, password: String): ArrayList<String>? {
        var fileInputStream: FileInputStream? = null
        try {
            val keyStore = KeyStore.getInstance(KeyStore.getDefaultType())
            fileInputStream = FileInputStream(path)
            keyStore.load(fileInputStream, password.toCharArray())
            val aliases = keyStore.aliases()
            val list = ArrayList<String>()
            while (aliases.hasMoreElements()) {
                list.add(aliases.nextElement())
            }
            return list
        } catch (_: Exception) {

        } finally {
            fileInputStream?.close()
        }
        return null
    }

    /**
     * 验证别名密码
     */
    fun verifyAlisaPassword(): Boolean {
        var fileInputStream: FileInputStream? = null
        try {
            val keyStore = KeyStore.getInstance(KeyStore.getDefaultType())
            fileInputStream = FileInputStream(apkSignatureState.keyStorePath)
            keyStore.load(fileInputStream, apkSignatureState.keyStorePassword.toCharArray())
            val alisa = apkSignatureState.keyStoreAlisaList?.getOrNull(apkSignatureState.keyStoreAlisaIndex)
            if (keyStore.containsAlias(alisa)) {
                val key = keyStore.getKey(alisa, apkSignatureState.keyStoreAlisaPassword.toCharArray())
                return key != null
            }
        } catch (_: Exception) {
            return false
        } finally {
            fileInputStream?.close()
        }
        return false
    }

    fun convertToJsonKotlin(jsonString: String): String {
        val jsonElement = JsonParser.parseString(jsonString)
        return generateKotlinDataClass("MyData", jsonElement)
    }

    fun convertToJsonJava(jsonString: String): String {
        val jsonElement = JsonParser.parseString(jsonString)
        return generateJavaClass("MyData", jsonElement)
    }

    private fun generateKotlinDataClass(className: String, jsonElement: JsonElement): String {
        return when (jsonElement) {
            is JsonObject -> {
                val properties = jsonElement.entrySet().joinToString(",\n") { entry ->
                    val propertyName = entry.key
                    val propertyType = determineType(entry.value)
                    "val $propertyName: $propertyType"
                }
                "data class $className {\n$properties\n}"
            }
            else -> throw IllegalArgumentException("Unsupported JSON type")
        }
    }

    private fun generateJavaClass(className: String, jsonElement: JsonElement): String {
        return when (jsonElement) {
            is JsonObject -> {
                val properties = jsonElement.entrySet().joinToString(",\n") { entry ->
                    val propertyName = entry.key
                    val propertyType = determineJavaType(entry.value)
                    "private ${propertyType} ${propertyName};"
                }
                val gettersSetters = jsonElement.entrySet().joinToString("\n") { entry ->
                    val propertyName = entry.key
                    val propertyType = determineJavaType(entry.value)
                    """
                    public ${propertyType} get${capitalize(propertyName)}() {
                        return ${propertyName};
                    }

                    public void set${capitalize(propertyName)}(${propertyType} ${propertyName}) {
                        this.${propertyName} = ${propertyName};
                    }
                    """.trimIndent()
                }
                "public class $className {\n$properties\n\n$gettersSetters\n}"
            }
            else -> throw IllegalArgumentException("Unsupported JSON type")
        }
    }

    private fun determineType(jsonElement: JsonElement): String {
        return when {
            jsonElement.isJsonObject -> "Map<String, Any?>"
            jsonElement.isJsonArray -> "List<Any?>"
            jsonElement.isJsonNull -> "Any?"
            jsonElement.isJsonPrimitive -> {
                val primitive = jsonElement.asJsonPrimitive
                when {
                    primitive.isNumber -> "Int" // 或者更通用的 Number
                    primitive.isBoolean -> "Boolean"
                    primitive.isString -> "String"
                    else -> "Any?"
                }
            }
            else -> "Any?"
        }
    }

    private fun determineJavaType(jsonElement: JsonElement): String {
        return when {
            jsonElement.isJsonObject -> "Map<String, Object>"
            jsonElement.isJsonArray -> "List<Object>"
            jsonElement.isJsonNull -> "Object"
            jsonElement.isJsonPrimitive -> {
                val primitive = jsonElement.asJsonPrimitive
                when {
                    primitive.isNumber -> "Integer" // 或者更通用的 Number
                    primitive.isBoolean -> "Boolean"
                    primitive.isString -> "String"
                    else -> "Object"
                }
            }
            else -> "Object"
        }
    }

    private fun capitalize(str: String): String {
        return str.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
    }
}

sealed interface UIState {
    data object WAIT : UIState
    data object Loading : UIState
    data class Success(val result: Any) : UIState
}