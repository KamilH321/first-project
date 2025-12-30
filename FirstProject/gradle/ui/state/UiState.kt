package com.example.firstproject.ui.state

import com.example.firstproject.db.entity.UserEntity

data class UiState(
    val currentUser: UserEntity? = null,
    val isLoading: Boolean = false
)
