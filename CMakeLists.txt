find_package(Java)
include(UseJava)

FILE(GLOB source
    "${CMAKE_CURRENT_SOURCE_DIR}/src/*/*.java")

add_jar(lox ${source} ENTRY_POINT lox/Lox)
