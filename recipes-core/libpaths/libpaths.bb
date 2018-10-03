SUMMARY = "Setup additional library paths"

LICENSE = "MIT"

# Ensures that even if USE_LDCONFIG is set to zero, the package will
# still be produced so that pkg_postinst_() works.
ALLOW_EMPTY_${PN} = "1"

do_install_append () {
	if [ "${USE_LDCONFIG}" = "1" ]; then
		mkdir -p ${D}${sysconfdir}/ld.so.conf.d
        echo "include /etc/ld.so.conf.d/*.conf" > ${D}${sysconfdir}/ld.so.conf
	fi
}

pkg_postinst_${PN} () {
    if [ -d $D/lib ] && ! [ -d $D/lib64 ]; then
        ln -s lib $D/lib64
    fi
}
