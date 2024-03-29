# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

#    # Add this global rule
#    -keepattributes Signature
#
#    # This rule will properly ProGuard all the model classes in
#    # the package com.yourcompany.models.
#    # Modify this rule to fit the structure of your app.
#    -keepclassmembers class com.yourcompany.models.** {
#      *;
#    }
   -keepclassmembers,allowobfuscation class * {
      @com.google.gson.annotations.SerializedName <fields>;
    }

#    -keepclassmembers class com.kocoukot.holdgame.ui.button.domain.** { <fields>; }

    -keep class com.kocoukot.holdgame.ui.button.domain.** { *; }
    -keep class com.kocoukot.holdgame.domain.model.** { *; }
    -keep class com.kocoukot.holdgame.domain.model.user.GameGlobalUser
    -keep class com.kocoukot.holdgame.domain.usecase.** { *; }
    -keep class com.kocoukot.holdgame.ui.button.domain.** { *; }

    -keepclassmembers class * implements java.io.Serializable {
            private static final java.io.ObjectStreamField[] serialPersistentFields;
            private void writeObject(java.io.ObjectOutputStream);
            private void readObject(java.io.ObjectInputStream);
            java.lang.Object writeReplace();
            java.lang.Object readResolve();
    }
        -keep class com.google.android.apps.authenticator.** {*;}