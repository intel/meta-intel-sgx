SUMMARY = "Objective Caml Compiler"

SECTION = "devel"

LICENSE = "QPL-1.0 & LGPL-2.0"

LIC_FILES_CHKSUM = "file://LICENSE;md5=ff92c64086fe28f9b64a5e4e3fe24ebb"

def get_sub_version(d):
    pvparts = d.getVar('PV').split('.')
    return '.'.join(pvparts[0:2])

SUB_PV = "${@get_sub_version(d)}"

SRC_URI = "http://caml.inria.fr/pub/distrib/ocaml-${SUB_PV}/ocaml-${PV}.tar.gz"

SRC_URI[md5sum] = "ef1a324608c97031cbd92a442d685ab7"
SRC_URI[sha256sum] = "928fb5f64f4e141980ba567ff57b62d8dc7b951b58be9590ffb1be2172887a72"

S = "${WORKDIR}/ocaml-${PV}"

do_configure () {
    ./configure -prefix ${prefix} -libdir ${libdir} -bindir ${bindir}
}

do_compile () {
    oe_runmake world.opt
}

PARALLEL_MAKEINST = ""

do_install_class-native () {
    oe_runmake install DESTDIR=${D}
    oe_runmake install

    for s in `find ${D}${bindir} -type f`; do
        sed -i -e "1s,#!.*ocamlrun.*,#!${USRBINPATH}/env ocamlrun," ${s}
    done
}

BBCLASSEXTEND = "native nativesdk"
