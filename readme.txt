命令备注：

提交到GitHub
git remote add origin https://github.com/jyfree/YFramework.git
git push -u origin master

编译错误，定位不了问题时，可输入如下命令
gradlew processDebugManifest --stacktrace

adb方式启动APP
adb shell am start -S -W com.jj.mitao2/jy.cn.com.yframework.MainActivity

anr
adb pull data/anr/traces.txt