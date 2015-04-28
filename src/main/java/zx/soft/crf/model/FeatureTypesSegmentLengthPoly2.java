package zx.soft.crf.model;

import zx.soft.crf.core.DataSequence;

public class FeatureTypesSegmentLengthPoly2 extends FeatureTypes {

	private static final long serialVersionUID = 220305L;

	float lenSq;
	short callNo;
	int maxSegLen;

	/**
	 * @param fgen
	 */
	public FeatureTypesSegmentLengthPoly2(FeatureGenImpl fgen, int maxSegLen) {
		super(fgen);
		this.maxSegLen = maxSegLen;
	}

	/* (non-Javadoc)
	 * @see iitb.Model.FeatureTypes#startScanFeaturesAt(iitb.CRF.DataSequence, int, int)
	 */
	@Override
	public boolean startScanFeaturesAt(DataSequence data, int prevPos, int pos) {
		lenSq = (float) (pos - prevPos) / maxSegLen;
		callNo = 0;
		return true;
	}

	/* (non-Javadoc)
	 * @see iitb.Model.FeatureTypes#hasNext()
	 */
	@Override
	public boolean hasNext() {
		return callNo < 2;
	}

	/* (non-Javadoc)
	 * @see iitb.Model.FeatureTypes#next(iitb.Model.FeatureImpl)
	 */
	@Override
	public void next(FeatureImpl f) {
		String name;
		if (callNo == 0) {
			f.val = lenSq;
			f.strId.id = 0;
		} else {
			f.val = lenSq * lenSq;
			f.strId.id = 1;
		}
		if (featureCollectMode())
			f.strId.name = (f.strId.id == 0) ? "LENGTH^1" : "LENGTH^2";
		callNo++;
	}

}