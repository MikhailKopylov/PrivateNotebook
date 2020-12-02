package com.amk.privatenotebook.core.database.interfaces

import androidx.lifecycle.LiveData
import com.amk.privatenotebook.core.user.User

interface RemoteUser {
    fun getCurrentUser(): LiveData<User?>
}