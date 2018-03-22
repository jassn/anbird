#!/bin/bash
#
#  Examples:
#    ./ota-incre.sh -i 20180329
#
#
#

check_arg(){
  if [ "x$P1" = "x" ]; then
     ARG="droid"
     LOG=${OUT_DIR}/BUILD-$ARG-$P2-`date +%Y%m%d-%H%M%S`.log
  elif [ "x$P1" = "xhelp" -o "x$P1" = "xh" ]; then
    cat <<HELP
    USAGE:
      1. cd Android_root
      2. make dist
      3. mv dist to desired path.
      4. edit this script - $P0.
HELP
    exit 1
  elif [ "x$P1" = "xmm" -o "x$P1" = "xm" -o "x$P1" = "xmmm" ]; then

    ARG=$P1
    MM_DIR=$P2
    PPP=`echo $P2 | sed "s/\//-/g"`
    LOG=${OUT_DIR}/BUILD-$ARG-$PPP-`date +%Y%m%d-%H%M%S`.log

  else
     # check value
     if [ "x$P1" = "xdroid" -o "x$P1" = "xsystemimage" -o "x$P1" = "xbootimage" -o "x$P1" = "xuboot" -o "x$P1" = "xotapackage" ]; then
       ARG=$P1
       LOG=${OUT_DIR}/BUILD-$ARG-$P2-`date +%Y%m%d-%H%M%S`.log
     else
       echo "command input error, please see help"
       echo "example: $0 help"
       exit 1
     fi

  fi
}


show_help(){
    cat <<HELP
    USAGE:
      1. cd Android_root
      2. make dist
      3. mv dist to desired path.
      4. edit this script - $P0.
HELP
}


export TOP=`pwd`
export OUT_DIR=out
mkdir -p $OUT_DIR

P0=$0
P1=$1
P2=$2
ARG=""
MM_DIR=""
LOG=""




check_arg
# build $ARG $MM_DIR 
###########################################

#BASE_PATH=~/Music
BASE_PATH=~/Pictures

PRODUCT=sabresd_6dq

    
## PREV_NUM=20180319
if [ "x$PREV_NUM" = "x" ]; then
    PREV_NUM=20180319
fi

CURR_NUM=20180321

PREV_PATH=$BASE_PATH/dist-$PREV_NUM
CURR_PATH=$BASE_PATH/dist-$CURR_NUM

PREV_TARGET=$PREV_PATH/$PRODUCT-target_files-$PREV_NUM.zip
CURR_TARGET=$CURR_PATH/$PRODUCT-target_files-$CURR_NUM.zip
INCRE_OTA=$BASE_PATH/$PRODUCT-incre-$PREV_NUM-to-$CURR_NUM.zip

echo "Old ...... $PREV_TARGET"
echo "New ...... $CURR_TARGET"
echo "Generating incremental OTA ...... $INCRE_OTA"

build/tools/releasetools/ota_from_target_files.py \
-i $PREV_TARGET $CURR_TARGET $INCRE_OTA








