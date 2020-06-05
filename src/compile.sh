CLASSIFIER="classifier/*.java "
DATAMODEL="datamodel/*.java datamodel/categories/*.java datamodel/classifications/*.java "
EXCEPTIONS="exceptions/*.java "
GRAPH="graph/*.java graph/edge/*.java graph/search/*.java "
MAIN="main/*.java main/tests/*.java "
METRICS="metrics/*.java metrics/perclass/*.java "
PARSER="parser/*.java parser/facades/*.java "
SCORE="score/*.java "
UTILS="utils/*.java "

TOTAL="$CLASSIFIER $DATAMODEL $EXCEPTIONS $GRAPH $MAIN $METRICS $PARSER $SCORE $UTILS"

javac $TOTAL
