#!/bin/bash
SCRIPT_PATH=${0%/*}
if [ "$0" != "$SCRIPT_PATH" ] && [ "$SCRIPT_PATH" != "" ]; then
    cd $SCRIPT_PATH
fi

############FLAGS#############
# cas: ctp-data-aggregator-service
# cts: ctp-tf-service
# tas: tc-data-aggregator-service
# rws: report-worker-service
# teps: tf-eig-proxy-service
# vis: tf-vehicle-imagery-service
# eig: dev.eig | ctp: dev.ctp | tc: dev.tc
cas=${cas:-false}
cts=${cts:-false}
tas=${tas:-false}
rws=${rws:-false}
teps=${teps:-false}
eig=${eig-false}
ctp=${ctp-false}
tc=${tc-false}
vis=${vis:-false}
rs=${rs:-docker-compose restart}

while [ $# -gt 0 ]; do
  case $1 in
    cas)
      cas="true"
      ;;
    cts)
      cts="true"
      ;;
    tas)
      tas="true"
      ;;
    rws)
      rws="true"
      ;;
    teps)
      teps="true"
      ;;
    eig)
      eig="true"
      ;;
    ctp)
      ctp="true"
      ;;
    tc)
      tc="true"
      ;;
    vis)
      vis="true"
      ;;
  esac
  shift
done

if [ $cas == true ]; then
  rs="${rs} ctp-data-aggregator-service"
fi
if [ $cts == true ]; then
  rs="${rs} ctp-tf-service"
fi
if [ $tas == true ]; then
  rs="${rs} tc-data-aggregator-service"
fi
if [ $rws == true ];then
  rs="${rs} report-worker-service"
fi
if [ $teps = true ]; then
  rs="${rs} tf-eig-proxy-service"
fi
if [ $eig = true ]; then
  rs="${rs} dev.eig"
fi
if [ $ctp = true ]; then
  rs="${rs} dev.ctp"
fi
if [ $tc = true ]; then
  rs="${rs} dev.tc"
fi
if [ vis = true ]; then
  docker="${docker} tf-vehicle-imagery-service"
fi

eval $rs
