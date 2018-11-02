This README file contains information on the contents of the
meta-intel-sgx layer to support Intel(R) SGX on Yocto.


Dependencies
============

1) meta-oe for protobuf recipe
URI: git://git.openembedded.org/meta-openembedded -b sumo

2) systemd initialization manager
URI: https://www.yoctoproject.org/docs/current/mega-manual/mega-manual.html#selecting-an-initialization-manager

Besides dependencies for SGX support on Yocto, Intel(R) SGX technology
needs to be supported by both the silicon as well as the boot firmware,
namely, UEFI BIOS.


Patches
=======

Please submit any patches via Github pull requests.

For discussion or patch submission via email, use the following:
meta-intel@lists.yoctoproject.org

Furthermore, in that email, make sure to copy the maintainer and add
"[meta-intel-sgx]" prefix to the subject.

Maintainer: Naveen R. Iyer <naveen.iyer@intel.com>


Adding the meta-intel-sgx layer to your Yocto build
===================================================

  1) bblayers.conf
  2) local.conf


bblayers.conf
=============

Add the location of the meta-intel-sgx layer to bblayers.conf, in
order to make the build system aware of it, along with any other layers
needed, for example:

  BBLAYERS ?= " \
    /path/to/yocto/meta \
    /path/to/yocto/meta-yocto \
    /path/to/yocto/meta-yocto-bsp \
    /path/to/yocto/meta-openembedded/meta-oe \
    /path/to/yocto/meta-intel-sgx \
    "


local.conf
==========

Add the following to local.conf in order to enable Intel(R) SGX without
the SGX SDK:

  # Enable Intel(R) SGX support.
  IMAGE_INSTALL_append = " sgx"

Add the following to local.conf in order to enable Intel(R) SGX,
including SGX SDK:
  # Enable Intel(R) SGX with SDK support.
  IMAGE_INSTALL_append = " sgx-dev"


Copying
=======

Unless noted otherwise, files are provided under the MIT license (see COPYING.MIT)
and are Copyright © Intel Corporation 2018.

