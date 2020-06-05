CLASSIFIER="classifier/ "
DATAMODEL="datamodel/ datamodel/categories/ datamodel/classifications/ "
EXCEPTIONS="exceptions/ "
GRAPH="graph/ graph/edge/ graph/search/ "
MAIN="main/ main/tests/ "
METRICS="metrics/ metrics/perclass/ "
PARSER="parser/ parser/facades/ "
SCORE="score/ "
UTILS="utils/ "

TOTAL="$CLASSIFIER $DATAMODEL $EXCEPTIONS $GRAPH $MAIN $METRICS $PARSER $SCORE $UTILS"

jar cfm Project.jar manif.txt $TOTAL