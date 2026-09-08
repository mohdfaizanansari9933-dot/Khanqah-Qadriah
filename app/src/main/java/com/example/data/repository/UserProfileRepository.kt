package com.example.data.repository

import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.json.JSONArray
import org.json.JSONObject

data class UserProfile(
    val id: String,
    val name: String,
    val email: String,
    val isGuest: Boolean = false,
    val title: String = "معتقد / زائرِ خانقاہ عالیہ قادریہ",
    val photoUrl: String? = null
)

data class SavedContentItem(
    val id: String,
    val title: String,
    val subtitle: String,
    val type: String, // "HADITH", "ASHAR", "QURAN", "BOOK"
    val content: String,
    val savedTimestamp: Long = System.currentTimeMillis()
)

data class ReminderPreferences(
    val fajrReminder: Boolean = true,
    val dhuhrReminder: Boolean = true,
    val asrReminder: Boolean = true,
    val maghribReminder: Boolean = true,
    val ishaReminder: Boolean = true,
    val dailyHadithReminder: Boolean = true,
    val ursAlerts: Boolean = true
)

data class AuthState(
    val user: UserProfile? = null,
    val isLoggedIn: Boolean = false
)

object UserProfileRepository {

    private const val PREFS_NAME = "khanqah_user_profile_prefs"
    private const val KEY_IS_LOGGED_IN = "key_is_logged_in"
    private const val KEY_USER_ID = "key_user_id"
    private const val KEY_USER_NAME = "key_user_name"
    private const val KEY_USER_EMAIL = "key_user_email"
    private const val KEY_IS_GUEST = "key_is_guest"
    private const val KEY_BOOKMARKS = "key_bookmarks"
    private const val KEY_SAVED_ITEMS = "key_saved_items"
    private const val KEY_REMINDERS = "key_reminders"

    private val _currentUser = MutableStateFlow<UserProfile?>(null)
    val currentUser: StateFlow<UserProfile?> = _currentUser.asStateFlow()

    private val _authState = MutableStateFlow(AuthState())
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    private val _savedItems = MutableStateFlow<List<SavedContentItem>>(emptyList())
    val savedItems: StateFlow<List<SavedContentItem>> = _savedItems.asStateFlow()

    private val _reminders = MutableStateFlow(ReminderPreferences())
    val reminders: StateFlow<ReminderPreferences> = _reminders.asStateFlow()

    private var sharedPreferences: SharedPreferences? = null

    private fun updateUser(profile: UserProfile) {
        _currentUser.value = profile
        _authState.value = AuthState(
            user = profile,
            isLoggedIn = !profile.isGuest
        )
    }

    fun initialize(context: Context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            loadFromPrefs()
        }
    }

    private fun loadFromPrefs() {
        val prefs = sharedPreferences ?: return
        val isLoggedIn = prefs.getBoolean(KEY_IS_LOGGED_IN, false)
        if (isLoggedIn) {
            val id = prefs.getString(KEY_USER_ID, "user_default") ?: "user_default"
            val name = prefs.getString(KEY_USER_NAME, "محبِ خانقاہ قادریہ") ?: "محبِ خانقاہ قادریہ"
            val email = prefs.getString(KEY_USER_EMAIL, "devotee@qadri.in") ?: "devotee@qadri.in"
            val isGuest = prefs.getBoolean(KEY_IS_GUEST, false)
            updateUser(UserProfile(id = id, name = name, email = email, isGuest = isGuest))
        } else {
            // Default to guest session if first launch
            updateUser(UserProfile(
                id = "guest_001",
                name = "زائرِ خانقاہ (مہمان)",
                email = "guest@khanqahqadriah.in",
                isGuest = true
            ))
        }

        // Load Saved items
        val savedJson = prefs.getString(KEY_SAVED_ITEMS, null)
        if (!savedJson.isNullOrEmpty()) {
            try {
                val array = JSONArray(savedJson)
                val list = mutableListOf<SavedContentItem>()
                for (i in 0 until array.length()) {
                    val obj = array.getJSONObject(i)
                    list.add(
                        SavedContentItem(
                            id = obj.getString("id"),
                            title = obj.getString("title"),
                            subtitle = obj.getString("subtitle"),
                            type = obj.getString("type"),
                            content = obj.getString("content"),
                            savedTimestamp = obj.optLong("savedTimestamp", System.currentTimeMillis())
                        )
                    )
                }
                _savedItems.value = list
            } catch (_: Exception) {}
        } else {
            // Prepopulate some soulful initial Khanqah Ashar & Hadith
            _savedItems.value = listOf(
                SavedContentItem(
                    id = "sample_hadith_1",
                    title = "रोज़ाना हदीस: इल्म की तलब",
                    subtitle = "किताब: सैफ़ुल जब्बार",
                    type = "HADITH",
                    content = "طَلَبُ الْعِلْمِ فَرِيضَةٌ عَلَى كُلِّ مُسْلِمٍ - इल्म हासिल करना हर मुसलमान पर फ़र्ज़ है।"
                ),
                SavedContentItem(
                    id = "sample_ashar_1",
                    title = "कलाम: हुज़ूर अतीफ़ मियां क़ादरी",
                    subtitle = "सदा-ए-क़ादरिया",
                    type = "ASHAR",
                    content = "सर पे जब साया-ए-ग़ौस-उल-वरा रहता है\nहर क़दम पर हमें अहसान-ए-ख़ुदा रहता है"
                )
            )
        }

        // Load Reminders
        val remJson = prefs.getString(KEY_REMINDERS, null)
        if (!remJson.isNullOrEmpty()) {
            try {
                val obj = JSONObject(remJson)
                _reminders.value = ReminderPreferences(
                    fajrReminder = obj.optBoolean("fajr", true),
                    dhuhrReminder = obj.optBoolean("dhuhr", true),
                    asrReminder = obj.optBoolean("asr", true),
                    maghribReminder = obj.optBoolean("maghrib", true),
                    ishaReminder = obj.optBoolean("isha", true),
                    dailyHadithReminder = obj.optBoolean("dailyHadith", true),
                    ursAlerts = obj.optBoolean("ursAlerts", true)
                )
            } catch (_: Exception) {}
        }
    }

    fun login(email: String, name: String? = null) {
        val displayName = if (!name.isNullOrBlank()) name else email.substringBefore("@").replaceFirstChar { it.uppercase() }
        val user = UserProfile(
            id = "user_" + System.currentTimeMillis(),
            name = displayName,
            email = email,
            isGuest = false
        )
        updateUser(user)
        sharedPreferences?.edit()?.apply {
            putBoolean(KEY_IS_LOGGED_IN, true)
            putString(KEY_USER_ID, user.id)
            putString(KEY_USER_NAME, user.name)
            putString(KEY_USER_EMAIL, user.email)
            putBoolean(KEY_IS_GUEST, false)
            apply()
        }
    }

    fun loginWithGoogle(accountName: String, accountEmail: String) {
        val user = UserProfile(
            id = "google_" + System.currentTimeMillis(),
            name = accountName,
            email = accountEmail,
            isGuest = false
        )
        updateUser(user)
        sharedPreferences?.edit()?.apply {
            putBoolean(KEY_IS_LOGGED_IN, true)
            putString(KEY_USER_ID, user.id)
            putString(KEY_USER_NAME, user.name)
            putString(KEY_USER_EMAIL, user.email)
            putBoolean(KEY_IS_GUEST, false)
            apply()
        }
    }

    fun continueAsGuest() {
        val user = UserProfile(
            id = "guest_" + System.currentTimeMillis(),
            name = "زائرِ خانقاہ (مہمان)",
            email = "guest@khanqahqadriah.in",
            isGuest = true
        )
        updateUser(user)
        sharedPreferences?.edit()?.apply {
            putBoolean(KEY_IS_LOGGED_IN, false)
            putString(KEY_USER_ID, user.id)
            putString(KEY_USER_NAME, user.name)
            putString(KEY_USER_EMAIL, user.email)
            putBoolean(KEY_IS_GUEST, true)
            apply()
        }
    }

    fun logout() {
        val guest = UserProfile(
            id = "guest_001",
            name = "زائرِ خانقاہ (مہمان)",
            email = "guest@khanqahqadriah.in",
            isGuest = true
        )
        updateUser(guest)
        sharedPreferences?.edit()?.apply {
            putBoolean(KEY_IS_LOGGED_IN, false)
            remove(KEY_USER_ID)
            remove(KEY_USER_NAME)
            remove(KEY_USER_EMAIL)
            putBoolean(KEY_IS_GUEST, true)
            apply()
        }
    }

    fun saveContentItem(item: SavedContentItem) {
        val currentList = _savedItems.value.toMutableList()
        if (currentList.none { it.id == item.id }) {
            currentList.add(0, item)
            _savedItems.value = currentList
            persistSavedItems(currentList)
        }
    }

    fun removeContentItem(itemId: String) {
        val currentList = _savedItems.value.filter { it.id != itemId }
        _savedItems.value = currentList
        persistSavedItems(currentList)
    }

    fun isItemSaved(itemId: String): Boolean {
        return _savedItems.value.any { it.id == itemId }
    }

    private fun persistSavedItems(items: List<SavedContentItem>) {
        val array = JSONArray()
        for (item in items) {
            val obj = JSONObject().apply {
                put("id", item.id)
                put("title", item.title)
                put("subtitle", item.subtitle)
                put("type", item.type)
                put("content", item.content)
                put("savedTimestamp", item.savedTimestamp)
            }
            array.put(obj)
        }
        sharedPreferences?.edit()?.putString(KEY_SAVED_ITEMS, array.toString())?.apply()
    }

    fun updateReminders(preferences: ReminderPreferences) {
        _reminders.value = preferences
        val obj = JSONObject().apply {
            put("fajr", preferences.fajrReminder)
            put("dhuhr", preferences.dhuhrReminder)
            put("asr", preferences.asrReminder)
            put("maghrib", preferences.maghribReminder)
            put("isha", preferences.ishaReminder)
            put("dailyHadith", preferences.dailyHadithReminder)
            put("ursAlerts", preferences.ursAlerts)
        }
        sharedPreferences?.edit()?.putString(KEY_REMINDERS, obj.toString())?.apply()
    }
}
