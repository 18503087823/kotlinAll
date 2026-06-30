# Mac Mobile CLI 全家桶 — 安装配置指南

> 📅 2026-06-30 | 适用于 macOS Ventura 及以上

---

## 0. 前置：安装 Homebrew

> ⚠️ 以下为**终端命令**，打开 Mac 终端（Terminal.app）后粘贴运行：

```bash
/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
```

> 运行过程中可能提示输入 Mac 登录密码（输入时不显示，输完按回车）。

---

## 1. adb — Android Debug Bridge

### 安装

> ⚠️ 以下为**终端命令**：

```bash
brew install android-platform-tools
```

### 验证

> ⚠️ 以下为**终端命令**：

```bash
adb version
adb devices
```

> 实体手机需开启 USB 调试；模拟器会自动识别。

---

## 2. hdc — 鸿蒙设备调试

### 2.1 下载安装 DevEco Studio

从 [华为开发者官网](https://developer.huawei.com/consumer/cn/deveco-studio/) 下载 **macOS 版** DevEco Studio 并安装。

### 2.2 确认 hdc 路径

> ⚠️ 以下为**终端命令**：

```bash
ls ~/Library/Huawei/Sdk/harmonyos/toolchains/hdc
```

如果能看到 `hdc` 文件，说明路径正确。

### 2.3 配置环境变量

> ⚠️ 以下为**终端命令**（逐行运行）：

```bash
echo 'export PATH="$HOME/Library/Huawei/Sdk/harmonyos/toolchains:$PATH"' >> ~/.zshrc
```

```bash
source ~/.zshrc
```

### 2.4 验证

> ⚠️ 以下为**终端命令**：

```bash
hdc version
hdc list targets
```

---

## 3. Fastlane — 自动化打包发布

### 3.1 环境依赖确认

> ⚠️ 以下为**终端命令**：

```bash
ruby -v
```

> Mac 自带 Ruby，版本 ≥ 2.6 即可。

如果提示没有 Xcode 命令行工具，运行：

> ⚠️ 以下为**终端命令**：

```bash
xcode-select --install
```

### 3.2 安装 Fastlane

> ⚠️ 以下为**终端命令**（二选一）：

方式一（推荐，用 Gem 安装）：

```bash
gem install fastlane -NV
```

方式二（Homebrew 安装）：

```bash
brew install fastlane
```

### 3.3 初始化（在项目根目录下执行）

> ⚠️ 以下为**终端命令**：

```bash
cd 你的项目目录/
fastlane init
```

> 执行后会生成 `fastlane/Appfile` 和 `fastlane/Fastfile` 两个配置文件。

### 3.4 Android 示例：`fastlane/Fastfile`

以下为 **fastlane 配置文件内容**（用文本编辑器编辑）：

```ruby
default_platform(:android)

platform :android do
  lane :debug do
    gradle(task: "assembleDebug")
  end

  lane :release do
    gradle(task: "assembleRelease")
  end
end
```

```bash
# 运行（终端命令）：
fastlane android debug
```

### 3.5 iOS 示例：`fastlane/Fastfile`

以下为 **fastlane 配置文件内容**（用文本编辑器编辑）：

```ruby
platform :ios do
  lane :beta do
    increment_build_number
    build_app(scheme: "YourApp")
    upload_to_testflight
  end
end
```

```bash
# 运行（终端命令）：
fastlane ios beta
```

> iOS 打包需要 Apple Developer 账号和有效的 provisioning profile。

---

## 4. Appium — 移动端 UI 自动化测试

### 4.1 安装 Node.js

> ⚠️ 以下为**终端命令**：

```bash
brew install node
node -v
```

> 确认版本 ≥ 18。

### 4.2 安装 Appium 服务端

> ⚠️ 以下为**终端命令**（逐行运行）：

```bash
npm i -g appium
```

```bash
appium driver install uiautomator2
```

```bash
appium driver install xcuitest
```

### 4.3 安装 Appium Inspector（元素定位工具）

> ⚠️ 以下为**终端命令**：

```bash
brew install --cask appium-inspector
```

### 4.4 配置 Android SDK 路径

> ⚠️ 以下为**终端命令**（逐行运行）：

```bash
echo 'export ANDROID_HOME="$HOME/Library/Android/sdk"' >> ~/.zshrc
```

```bash
echo 'export PATH="$ANDROID_HOME/platform-tools:$ANDROID_HOME/tools:$PATH"' >> ~/.zshrc
```

```bash
source ~/.zshrc
```

### 4.5 安装 Appium Doctor（环境自检）

> ⚠️ 以下为**终端命令**：

```bash
npm i -g appium-doctor
```

### 4.6 环境检查

> ⚠️ 以下为**终端命令**：

```bash
appium-doctor --android
```

```bash
appium-doctor --ios
```

### 4.7 启动 Appium

> ⚠️ 以下为**终端命令**：

```bash
appium
```

### 4.8 编写测试用例

**Node.js 方案**（终端命令）：

```bash
mkdir my-tests && cd my-tests
npm init -y
npm i webdriverio @wdio/cli
```

**Python 方案**（终端命令）：

```bash
pip install Appium-Python-Client
```

---

## 5. 最终验证清单

全部装完后，逐条运行以下终端命令确认无误：

```bash
# 终端命令 ↓
brew --version
```

```bash
# 终端命令 ↓
adb version
```

```bash
# 终端命令 ↓
hdc version
```

```bash
# 终端命令 ↓
fastlane --version
```

```bash
# 终端命令 ↓
appium --version
```

```bash
# 终端命令 ↓
appium-doctor --android
```

```bash
# 终端命令 ↓
appium-doctor --ios
```

全部不报错 = 全家桶齐活 🎉

---

## 版本汇总

| 工具 | 安装方式 | 用途 |
|------|---------|------|
| Homebrew | 官方脚本 | Mac 包管理器（前置） |
| `adb` | `brew install android-platform-tools` | Android 设备调试 |
| `hdc` | DevEco Studio 自带 | 鸿蒙设备调试 |
| `fastlane` | `gem install fastlane` | 自动化打包/发布 |
| `appium` | `npm i -g appium` | 移动端 UI 自动化测试 |
| `appium-doctor` | `npm i -g appium-doctor` | Appium 环境自检 |
