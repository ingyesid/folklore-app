# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.kts.
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

-dontwarn com.folklore.data.database.EventDao
-dontwarn com.folklore.data.datasource.LocalPreferencesDataSource
-dontwarn com.folklore.data.remote.FolkloreAPI
-dontwarn com.folklore.data.repository.UserPreferencesRepositoryImpl
-dontwarn com.folklore.di.CoroutineDispatchersModule_ProvidesIoDispatcherFactory
-dontwarn com.folklore.di.IoDispatcher
-dontwarn com.folklore.di.RepositoryModule_ProvideAddFavoriteUseCaseFactory
-dontwarn com.folklore.di.RepositoryModule_ProvideCheckIfEventIsFavoriteUseCaseFactory
-dontwarn com.folklore.di.RepositoryModule_ProvideEntityMapperFactory
-dontwarn com.folklore.di.RepositoryModule_ProvideEventDtoMapperFactory
-dontwarn com.folklore.di.RepositoryModule_ProvideEventsDAOFactory
-dontwarn com.folklore.di.RepositoryModule_ProvideEventsDatabaseFactory
-dontwarn com.folklore.di.RepositoryModule_ProvideEventsLocalDataSourceFactory
-dontwarn com.folklore.di.RepositoryModule_ProvideEventsRemoteDataSourceFactory
-dontwarn com.folklore.di.RepositoryModule_ProvideEventsRepositoryFactory
-dontwarn com.folklore.di.RepositoryModule_ProvideFolkloreAPIFactory
-dontwarn com.folklore.di.RepositoryModule_ProvideGetAllEventsUseCaseFactory
-dontwarn com.folklore.di.RepositoryModule_ProvideGetAllFavoritesUseCaseFactory
-dontwarn com.folklore.di.RepositoryModule_ProvideOkHttpFactory
-dontwarn com.folklore.di.RepositoryModule_ProvidePreferencesDataStoreFactory
-dontwarn com.folklore.di.RepositoryModule_ProvideRemoveFromFavoriteUseCaseFactory