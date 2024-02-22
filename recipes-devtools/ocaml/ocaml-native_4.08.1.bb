SUMMARY = "OCaml is a ML language implementation with a class-based object system."
HOMEPAGE = "https://https://github.com/ocaml/ocaml"

LICENSE = "LGPL-2.1-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=4f72f33f302a53dc329f4d3819fe14f9"

# Packages supported
# ocaml-native : Package built on build machine.
inherit native

# Parallel make does not work with some recipes
PARALLEL_MAKE = ""
PARALLEL_MAKEINST = ""

# Source repo
SRC_URI += "https://github.com/ocaml/ocaml/archive/4.08.1.tar.gz;sha256sum=b53ed3d487b83fd49bc181bded066ae8e6fb592cf40514261d27d36050d5db85"
SRC_URI += "file://0001-4.08.1-link-error.patch"
SRC_URI += "file://0002-4.08.1-shebang-use-env.patch"
SRC_URI += "file://0001-Fix-ocaml-compilation-in-ubuntu-22.04-LTS.patch"
S = "${WORKDIR}/ocaml-4.08.1"

do_configure () {
    ./configure --prefix="${prefix}" --enable-debugger=no --disable-ocamldoc --with-x=no
}

do_compile () {
    oe_runmake USRBINPATH="${USRBINPATH}" world.opt
}

do_install () {
    oe_runmake DESTDIR="${D}" install

    for s in `find "${D}${bindir}" -type f`; do
        sed -i -e "1s,#!.*ocamlrun.*,#!${USRBINPATH}/env ocamlrun," ${s}
    done
}

