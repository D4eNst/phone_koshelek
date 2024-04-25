package com.d4enst.laba_1_koshelek.navigation

enum class Page(val route: String) {
    PATTERNS_LIST("patterns_list"),
    PATTERN_CRUD("pattern_crud"),
    OBJECTS_LIST("objects_list"),
    OBJECT_CRUD("object_crud"),
}

enum class PageParam(val value: String) {
    IS_EDITABLE("true"),
    IS_NOT_EDITABLE("false"),
    DEFAULT_ID("0"),
}
