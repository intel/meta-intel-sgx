
### source ###

# Source repo
SRC_URI = "gitsm://github.com/intel/linux-sgx.git"
SRCREV = "a21f2d9dd77e7672b00f99f9b61bc81cc44e5954"

SRC_URI += "https://download.01.org/intel-sgx/sgx-linux/2.12/optimized_libs_update_2.12.tar.gz;name=optimized_libs;subdir=${S}"
SRC_URI[optimized_libs.sha256sum] = "367bd7b9579f0d418aeba2467c9c75a17f2e4e84a7d0a688e1ef8367aa4da0a4"

SRC_URI += "https://download.01.org/intel-sgx/linux-2.3/prebuilt_ae_2.3.tar.gz;name=prebuilt_ae;subdir=${S}"
SRC_URI[prebuilt_ae.sha256sum] = "d171136f28f10f76f8ecf1ff91f8d9772b053c035efc9bb29b9ff56af442548a"

SRC_URI += "https://github.com/01org/intel-sgx-ssl/archive/lin_2.11_1.1.1g.zip;name=sgxssl;subdir=${WORKDIR}/sgxssl"
SRC_URI[sgxssl.sha256sum] = "12828839c4555e0f5e88e86db090c995053d98d99091862c498fc55f379183fc"

SRC_URI += "https://www.openssl.org/source/openssl-1.1.1g.tar.gz;name=openssl;subdir=${WORKDIR}/openssl"
SRC_URI[openssl.sha256sum] = "ddb04774f1e32f0c49751e21b67216ac87852ceb056b75209af2443400636d46"

SRC_URI += "https://download.01.org/intel-sgx/sgx-dcap/1.9/linux/prebuilt_dcap_1.9.tar.gz;name=prebuilt_dcap;subdir=${S}/external/dcap_source/QuoteGeneration"
SRC_URI[prebuilt_dcap.sha256sum] = "8cd0249ee49dbfd589b257cd0fa14d374d01b6c210ca7d0a14e618a48bbdb82b"

### configure ###

do_configure_prepend () {
    # SRC_URI is located at ${S}

    # optimized libs are unpacke in ${S}

    # prebuilt ae is unpacked in ${S}

    # Move intel-sgx-ssl library to external/sgxssl
    if [ -d "${WORKDIR}/sgxssl" ]; then
        mv ${WORKDIR}/sgxssl/intel-sgx-ssl-lin_2.11_1.1.1g/* "${S}/external/sgxssl"
        rm -rf "${WORKDIR}/sgxssl"
    fi

    # Move openssl source to correct location
    if [ -d "${WORKDIR}/openssl" ]; then
        mv "${WORKDIR}/openssl/openssl-1.1.1g" "${S}/external/sgxssl/openssl_source"
        rm -rf "${WORKDIR}/openssl"
    fi

    # Patch dcap source
    if [ -f "${S}/external/openmp/0001-Enable-OpenMP-in-SGX.patch" ]; then
        ( cd "${S}/external/openmp/openmp_code" && git apply "../0001-Enable-OpenMP-in-SGX.patch" ||  git apply "../0001-Enable-OpenMP-in-SGX.patch" --check -R )
        rm "${S}/external/openmp/0001-Enable-OpenMP-in-SGX.patch"
    fi
}

