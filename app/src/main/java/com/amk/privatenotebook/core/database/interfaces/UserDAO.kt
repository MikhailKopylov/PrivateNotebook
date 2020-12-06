package com.amk.privatenotebook.core.database.interfaces

import androidx.lifecycle.LiveData
import com.amk.privatenotebook.core.user.User

interface UserDAO {
    fun getCurrentUser(): LiveData<User?>
}