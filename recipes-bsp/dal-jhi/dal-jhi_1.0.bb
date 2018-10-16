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
DESCRIPTION = "Dynamic Application Loader (DAL) Host Interface (JHI)"

LICENSE = "Apache-2.0"

LIC_FILES_CHKSUM = "file://../LICENSE-2.0.txt;md5=3b83ef96387f14655fc854ddc3c6bd57"

# No information for SRC_URI yet (only an external source tree was specified)
SRC_URI = "git://github.com/intel/dynamic-application-loader-host-interface.git \
	http://www.apache.org/licenses/LICENSE-2.0.txt"
SRC_URI[md5sum] = "3b83ef96387f14655fc854ddc3c6bd57"

SRCREV = "3414d9849a7472d2431d864a96865d54d4c0cf42"

S = "${WORKDIR}/git"

inherit cmake pkgconfig systemd

PACKAGES = "${PN} ${PN}-dbg"
FILES_${PN} += "${sysconfdir}/* ${baselibdir}/* ${libdir}/* ${sbindir}/*"

# Specify any options you want to pass to cmake using EXTRA_OECMAKE:
#EXTRA_OECMAKE = ""
# NOTE: the following library dependencies are unknown, ignoring: atex oad unix inkall ink curses atextitle threadsnat t dopt
#       (this is based on recipes that have previously been built and packaged)
# NOTE: some of these dependencies may be optional, check the Makefile and/or upstream documentation
DEPENDS += "systemd libxml2"

# NOTE: this is a Makefile-only piece of software, so we cannot generate much of the
# recipe automatically - you will need to examine the Makefile yourself and ensure
# that the appropriate arguments are passed in.
#EXTRA_OECMAKE_prepend = "${S} "
#do_configure () {
#	# Specify any needed configure commands here
#	EXTRA_OECMAKE_prepend = "${S} "
#}

do_compile () {
	# You will almost certainly need to add additional arguments here
	oe_runmake 
}

do_install_class-target() {
	# This is a guess; additional arguments may be required
	oe_runmake install DESTDIR=${D}
	rm -rf ${D}/usr/src/debug
}

inherit systemd
SYSTEMD_SERVICE_${PN} = "jhi.service"

