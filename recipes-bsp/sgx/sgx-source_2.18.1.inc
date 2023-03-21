
### source ###

# Source repo
SRC_URI = "gitsm://github.com/intel/linux-sgx.git;branch=master;protocol=https"
SRCREV = "607a226fc38e66e9b68f12c1b8b68045a5e064f8"

SRC_URI += "https://download.01.org/intel-sgx/sgx-linux/2.18.1/optimized_libs_2.18.1.tar.gz;name=optimized_libs;subdir=${S}"
SRC_URI[optimized_libs.sha256sum] = "900c65a70200049bffc8ac05b4d4f941e06ca5753869d9af5e3ed1a1d0c8ba2d"

SRC_URI += "https://download.01.org/intel-sgx/sgx-linux/2.18.1./prebuilt_ae_2.18.1.tar.gz;name=prebuilt_ae;subdir=${S}"
SRC_URI[prebuilt_ae.sha256sum] = "402dd1aaac23f782600e493a698cc35761d50d8f1979ad543d61bd91a0a60b2f"

SRC_URI += "https://github.com/intel/intel-sgx-ssl/archive/lin_2.18_1.1.1q.zip;name=sgxssl;subdir=${WORKDIR}/sgxssl"
SRC_URI[sgxssl.sha256sum] = "6c33d2178b6b01bdbb1f97804ae14aec13544b0cb45902a0906c20ef7b4032bc"

SRC_URI += "https://www.openssl.org/source/old/1.1.1/openssl-1.1.1q.tar.gz;name=openssl;subdir=${WORKDIR}/openssl"
SRC_URI[openssl.sha256sum] = "d7939ce614029cdff0b6c20f0e2e5703158a489a72b2507b8bd51bf8c8fd10ca"

SRC_URI += "https://download.01.org/intel-sgx/sgx-dcap/1.15/linux/prebuilt_dcap_1.15.tar.gz;name=prebuilt_dcap;subdir=${S}/external/dcap_source/QuoteGeneration"
SRC_URI[prebuilt_dcap.sha256sum] = "b7a16cd939ec632363bb69f8df0bf60835d06adafb70e15ba341ef4e1d37ed36"

SRC_URI += "file://0003-default-python.patch"

### configure ###

do_configure:prepend () {
    # SRC_URI is located at ${S}

    # optimized libs are unpacke in ${S}

    # prebuilt ae is unpacked in ${S}

    # Move intel-sgx-ssl library to external/sgxssl
    if [ -d "${WORKDIR}/sgxssl" ]; then
        mv ${WORKDIR}/sgxssl/intel-sgx-ssl-lin_2.18_1.1.1q/* "${S}/external/sgxssl"
        rm -rf "${WORKDIR}/sgxssl"
    fi

    # Move openssl source to correct location
    if [ -d "${WORKDIR}/openssl" ]; then
        mv "${WORKDIR}/openssl/openssl-1.1.1q" "${S}/external/sgxssl/openssl_source"
        rm -rf "${WORKDIR}/openssl"
    fi

    # Patch dcap source
    if [ -f "${S}/external/openmp/0001-Enable-OpenMP-in-SGX.patch" ]; then
        ( cd "${S}/external/openmp/openmp_code" && git apply "../0001-Enable-OpenMP-in-SGX.patch" ||  git apply "../0001-Enable-OpenMP-in-SGX.patch" --check -R )
        rm "${S}/external/openmp/0001-Enable-OpenMP-in-SGX.patch"
    fi

    cd ${S} && ./external/sgx-emm/create_symlink.sh
}
