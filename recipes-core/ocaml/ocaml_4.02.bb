# Recipe created by recipetool
# This is the basis of a recipe and may need further editing in order to be fully functional.
# (Feel free to remove these comments when editing.)

# WARNING: the following LICENSE and LIC_FILES_CHKSUM values are best guesses - it is
# your responsibility to verify that the values are complete and correct.
#
# NOTE: multiple licenses have been detected; they have been separated with &
# in the LICENSE value for now since it is a reasonable assumption that all
# of the licenses apply. If instead there is a choice between the multiple
# licenses then you should change the value to separate the licenses with |
# instead of &. If there is any doubt, check the accompanying documentation
# to determine which situation is applicable.
#
# The following license files were not able to be identified and are
# represented as "Unknown" below, you will need to check them yourself:
#   LICENSE
#
DESCRIPTION = "Objective Caml Compiler"
SECTION = "devel"
LICENSE = "LGPL"

LIC_FILES_CHKSUM = "file://LICENSE;md5=ff92c64086fe28f9b64a5e4e3fe24ebb"


# No information for SRC_URI yet (only an external source tree was specified)
SRC_URI = "http://caml.inria.fr/pub/distrib/ocaml-${PV}/ocaml-${PV}.3.tar.gz"

SRC_URI[md5sum] = "ef1a324608c97031cbd92a442d685ab7"

S = "${WORKDIR}/ocaml-${PV}.3"

inherit native

# NOTE: the following library dependencies are unknown, ignoring: atex oad unix inkall ink curses atextitle threadsnat t dopt
#       (this is based on recipes that have previously been built and packaged)
# NOTE: some of these dependencies may be optional, check the Makefile and/or upstream documentation
#DEPENDS = "libx11 flex-native bison-native"
#DEPENDS_append_class-native= "libx11 flex-native bison-native"

# NOTE: this is a Makefile-only piece of software, so we cannot generate much of the
# recipe automatically - you will need to examine the Makefile yourself and ensure
# that the appropriate arguments are passed in.
do_configure () {
	# Specify any needed configure commands here
	./configure -prefix ${prefix} -libdir ${libdir} -bindir ${bindir}
}

do_compile () {
	# You will almost certainly need to add additional arguments here
	oe_runmake world.opt
}

do_install_class-native () {
	# This is a guess; additional arguments may be required
	oe_runmake install DESTDIR=${D}
	oe_runmake install
}

BBCLASSEXTEND = "native nativesdk"
