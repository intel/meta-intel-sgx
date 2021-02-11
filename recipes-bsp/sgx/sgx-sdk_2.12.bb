# Packages supported
# sgx-sdk          : SGX Software Development Kit (SDK). Required to build the runtime package / sample code.

### common ###

require sgx-common_2.12.inc

# Packages required to build.
DEPENDS_append = " ocaml-native ocamlbuild-native"

### patch ###

SRC_URI += " \
    file://0001-sdk-configure.patch              \
    file://0002-install-sysroot.patch            \
"

SRC_URI_append_class-native = " \
    file://0011-add-sysroot-libdir-native.patch  \
"

SRC_URI_append_class-target = " \
    file://0021-fix-include-path.patch           \
"

### compile ###

# Build environment variables
EXTRA_OEMAKE += "OCAMLLIB='${STAGING_LIBDIR_NATIVE}/ocaml'"

# Build target(s)
EXTRA_OEMAKE += "sdk_install_pkg"

### install ###

do_install () {
    "${S}/linux/installer/bin/sgx_linux_x64_sdk_2.12.100.3.bin" --prefix="${D}${sgxdirprefix}${sgxrootdir}"
}

### package ###

SYSROOT_DIRS += "${sgxdirprefix}${sgxrootdir}"

INSANE_SKIP_${PN}-dev = "dev-elf staticdev ldflags"

FILES_${PN}-dev += "${sgxsdkpath}"

# Dev only package.
# Allowing an empty package enables the dev package for sdk images
ALLOW_EMPTY_${PN}     = "1"

### ###

BBCLASSEXTEND = "native"

CVE_PRODUCT = "software_guard_extensions_sdk"

