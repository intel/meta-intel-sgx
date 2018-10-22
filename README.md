This README file contains information on the contents of the
meta-intel-sgx layer.

Please see the corresponding sections below for details.

Dependencies
============

This layer depends on:
1) meta-oe for protobuf recipe
URI: git://git.openembedded.org/meta-openembedded -b sumo

2) systemd initialization manager
URI: https://www.yoctoproject.org/docs/current/mega-manual/mega-manual.html#selecting-an-initialization-manager

Besides dependencies for SGX support on Yocto, Intel(R) SGX technology
needs to be supported by both the silicon as well as the boot firmware, 
namely, UEFI BIOS.

Patches
=======

Please submit any patches against the meta-intel-sgx layer via Github
pull requests.

For discussion or patch submission via email, use the following:
meta-intel@lists.yoctoproject.org

When submitting patches that way, make sure to copy the maintainer 
and add a "[meta-intel-sgx]" prefix to the subject of the emails.

Maintainer: Naveen R. Iyer <naveen.iyer@intel.com>

Table of Contents
=================

1) Adding the meta-intel-sgx layer to your build

1) Adding the meta-intel-sgx layer to your build
================================================
In order to use this layer, you need to make the build system aware of
it.

Assuming the security repository exists at the top-level of your
yocto build tree, you can add it to the build system by adding the
location of the meta-intel-sgx layer to bblayers.conf, along with any
other layers needed. e.g.:

  BBLAYERS ?= " \
    /path/to/yocto/meta \
    /path/to/yocto/meta-yocto \
    /path/to/yocto/meta-yocto-bsp \
    /path/to/yocto/meta-openembedded/meta-oe \
    /path/to/yocto/meta-intel-sgx \
    "

Just adding the layer does not enable Intel(R) SGX. These changes can be
enabled and disabled separately via configuration variables. To enable 
Intel(R) SGX, add the following entry to local.conf:
  # Enable Intel(R) SGX support.
  IMAGE_INSTALL_append = " sgx"

  # Enable Intel(R) SGX with SDK support.
  IMAGE_INSTALL_append = " sgx-dev"
