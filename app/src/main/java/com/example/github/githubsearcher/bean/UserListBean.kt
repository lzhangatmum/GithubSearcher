package com.example.github.githubsearcher.bean

data class UserListBean(
    var total_count: Int,
    var incomplete_results: Boolean,
    var items :List<UserBean>

)