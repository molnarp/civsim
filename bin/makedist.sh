#!/bin/sh
V="0.1"

cd `dirname $(readlink -f $0)`/../
ant clean jar
mv dist civsim-${V}
tar cfz civsim-${V}.tar.gz civsim-${V}
zip -r civsim-${V}.zip civsim-${V}

rm -rf civsim-${V}

