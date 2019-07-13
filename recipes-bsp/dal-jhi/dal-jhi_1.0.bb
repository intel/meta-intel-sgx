DESCRIPTION = "Dynamic Application Loader (DAL) Host Interface (JHI)"

LICENSE = "Apache-2.0"

LIC_FILES_CHKSUM = "file://../LICENSE-2.0.txt;md5=3b83ef96387f14655fc854ddc3c6bd57"

# No information for SRC_URI yet (only an external source tree was specified)
SRC_URI = "git://github.com/intel/dynamic-application-loader-host-interface.git \
	http://www.apache.org/licenses/LICENSE-2.0.txt"
SRC_URI[md5sum] = "3b83ef96387f14655fc854ddc3c6bd57"

SRCREV = "3414d9849a7472d2431d864a96865d54d4c0cf42"

S = "${WORKDIR}/git"

inherit pkgconfig systemd

PACKAGES = "${PN} ${PN}-dbg"
FILES_${PN} += "${sysconfdir}/* ${baselibdir}/* ${libdir}/* ${sbindir}/*"

DEPENDS += "systemd libxml2 icu"

inherit cmake autotools

do_compile () {
	cd ${S}
	cmake . && make
}

do_install_class-target() {
	cd ${S}
	oe_runmake install DESTDIR=${D}
}

SYSTEMD_SERVICE_${PN} = "jhi.service"

