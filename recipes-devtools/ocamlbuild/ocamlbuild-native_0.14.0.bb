SUMMARY = "OCamlbuild is a generic build tool, that has built-in rules for building OCaml library and programs."
HOMEPAGE = "https://github.com/ocaml/ocamlbuild"

LICENSE = "GPL-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=5123b1988300c0d24c79e04f09d86dc0"

# Packages supported
# ocamlbuild-native : Package built on build machine.
inherit native

# Packages required to build.
DEPENDS += "ocaml-native"

# Parallel make does not work with some recipes
PARALLEL_MAKE = ""
PARALLEL_MAKEINST = ""

# Source repo
SRC_URI += "https://github.com/ocaml/ocamlbuild/archive/0.14.0.tar.gz;sha256sum=87b29ce96958096c0a1a8eeafeb6268077b2d11e1bf2b3de0f5ebc9cf8d42e78"
SRC_URI += "file://0001-makefile-change-include-path.patch"
S = "${WORKDIR}/ocamlbuild-0.14.0"

do_configure () {
    oe_runmake OCAMLBUILD_BINDIR="${bindir}" configure
}

do_compile () {
    oe_runmake OCAMLLIB="${libdir}/ocaml"
}

do_install () {
    oe_runmake DESTDIR="${D}" install
}

