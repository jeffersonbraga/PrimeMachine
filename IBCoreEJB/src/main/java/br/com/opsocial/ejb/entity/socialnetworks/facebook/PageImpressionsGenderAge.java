package br.com.opsocial.ejb.entity.socialnetworks.facebook;

public class PageImpressionsGenderAge {

	private Integer maleThirteenAmongSeventeen = 0;
	private Integer maleEighteenAmongTwentyFour = 0;
	private Integer maleTwentyFiveAmongThirtyFour = 0;
	private Integer maleThirtyFiveAmongFourtyFour = 0;
	private Integer maleFourtyFiveAmongFiftyFour = 0;
	private Integer maleFiftyFiveAmongSixtyFour = 0;
	private Integer maleSixtyFivePlus = 0;

	private Integer femaleThirteenAmongSeventeen = 0;
	private Integer femaleEighteenAmongTwentyFour = 0;
	private Integer femaleTwentyFiveAmongThirtyFour = 0;
	private Integer femaleThirtyFiveAmongFourtyFour = 0;
	private Integer femaleFourtyFiveAmongFiftyFour = 0;
	private Integer femaleFiftyFiveAmongSixtyFour = 0;
	private Integer femaleSixtyFivePlus = 0;
	
	public Integer sumMale() {
		return (maleThirteenAmongSeventeen+maleEighteenAmongTwentyFour+
				maleTwentyFiveAmongThirtyFour+maleThirtyFiveAmongFourtyFour+
				maleFourtyFiveAmongFiftyFour+maleFiftyFiveAmongSixtyFour+
				maleSixtyFivePlus);
	}
	
	public Integer sumFemale() {
		return (femaleThirteenAmongSeventeen+femaleEighteenAmongTwentyFour+
				femaleTwentyFiveAmongThirtyFour+femaleThirtyFiveAmongFourtyFour+
				femaleFourtyFiveAmongFiftyFour+femaleFiftyFiveAmongSixtyFour+
				femaleSixtyFivePlus);
	}

	/**
	 * @return the maleThirteenAmongSeventeen
	 */
	public Integer getMaleThirteenAmongSeventeen() {
		return maleThirteenAmongSeventeen;
	}
	/**
	 * @param maleThirteenAmongSeventeen the maleThirteenAmongSeventeen to set
	 */
	public void setMaleThirteenAmongSeventeen(Integer maleThirteenAmongSeventeen) {
		this.maleThirteenAmongSeventeen = maleThirteenAmongSeventeen;
	}
	/**
	 * @return the maleEighteenAmongTwentyFour
	 */
	public Integer getMaleEighteenAmongTwentyFour() {
		return maleEighteenAmongTwentyFour;
	}
	/**
	 * @param maleEighteenAmongTwentyFour the maleEighteenAmongTwentyFour to set
	 */
	public void setMaleEighteenAmongTwentyFour(Integer maleEighteenAmongTwentyFour) {
		this.maleEighteenAmongTwentyFour = maleEighteenAmongTwentyFour;
	}
	/**
	 * @return the maleTwentyFiveAmongThirtyFour
	 */
	public Integer getMaleTwentyFiveAmongThirtyFour() {
		return maleTwentyFiveAmongThirtyFour;
	}
	/**
	 * @param maleTwentyFiveAmongThirtyFour the maleTwentyFiveAmongThirtyFour to set
	 */
	public void setMaleTwentyFiveAmongThirtyFour(
			Integer maleTwentyFiveAmongThirtyFour) {
		this.maleTwentyFiveAmongThirtyFour = maleTwentyFiveAmongThirtyFour;
	}
	/**
	 * @return the maleThirtyFiveAmongFourtyFour
	 */
	public Integer getMaleThirtyFiveAmongFourtyFour() {
		return maleThirtyFiveAmongFourtyFour;
	}
	/**
	 * @param maleThirtyFiveAmongFourtyFour the maleThirtyFiveAmongFourtyFour to set
	 */
	public void setMaleThirtyFiveAmongFourtyFour(
			Integer maleThirtyFiveAmongFourtyFour) {
		this.maleThirtyFiveAmongFourtyFour = maleThirtyFiveAmongFourtyFour;
	}
	/**
	 * @return the maleFourtyFiveAmongFiftyFour
	 */
	public Integer getMaleFourtyFiveAmongFiftyFour() {
		return maleFourtyFiveAmongFiftyFour;
	}
	/**
	 * @param maleFourtyFiveAmongFiftyFour the maleFourtyFiveAmongFiftyFour to set
	 */
	public void setMaleFourtyFiveAmongFiftyFour(Integer maleFourtyFiveAmongFiftyFour) {
		this.maleFourtyFiveAmongFiftyFour = maleFourtyFiveAmongFiftyFour;
	}
	/**
	 * @return the maleFiftyFiveAmongSixtyFour
	 */
	public Integer getMaleFiftyFiveAmongSixtyFour() {
		return maleFiftyFiveAmongSixtyFour;
	}
	/**
	 * @param maleFiftyFiveAmongSixtyFour the maleFiftyFiveAmongSixtyFour to set
	 */
	public void setMaleFiftyFiveAmongSixtyFour(Integer maleFiftyFiveAmongSixtyFour) {
		this.maleFiftyFiveAmongSixtyFour = maleFiftyFiveAmongSixtyFour;
	}
	/**
	 * @return the maleSixtyFivePlus
	 */
	public Integer getMaleSixtyFivePlus() {
		return maleSixtyFivePlus;
	}
	/**
	 * @param maleSixtyFivePlus the maleSixtyFivePlus to set
	 */
	public void setMaleSixtyFivePlus(Integer maleSixtyFivePlus) {
		this.maleSixtyFivePlus = maleSixtyFivePlus;
	}
	/**
	 * @return the femaleThirteenAmongSeventeen
	 */
	public Integer getFemaleThirteenAmongSeventeen() {
		return femaleThirteenAmongSeventeen;
	}
	/**
	 * @param femaleThirteenAmongSeventeen the femaleThirteenAmongSeventeen to set
	 */
	public void setFemaleThirteenAmongSeventeen(Integer femaleThirteenAmongSeventeen) {
		this.femaleThirteenAmongSeventeen = femaleThirteenAmongSeventeen;
	}
	/**
	 * @return the femaleEighteenAmongTwentyFour
	 */
	public Integer getFemaleEighteenAmongTwentyFour() {
		return femaleEighteenAmongTwentyFour;
	}
	/**
	 * @param femaleEighteenAmongTwentyFour the femaleEighteenAmongTwentyFour to set
	 */
	public void setFemaleEighteenAmongTwentyFour(
			Integer femaleEighteenAmongTwentyFour) {
		this.femaleEighteenAmongTwentyFour = femaleEighteenAmongTwentyFour;
	}
	/**
	 * @return the femaleTwentyFiveAmongThirtyFour
	 */
	public Integer getFemaleTwentyFiveAmongThirtyFour() {
		return femaleTwentyFiveAmongThirtyFour;
	}
	/**
	 * @param femaleTwentyFiveAmongThirtyFour the femaleTwentyFiveAmongThirtyFour to set
	 */
	public void setFemaleTwentyFiveAmongThirtyFour(
			Integer femaleTwentyFiveAmongThirtyFour) {
		this.femaleTwentyFiveAmongThirtyFour = femaleTwentyFiveAmongThirtyFour;
	}
	/**
	 * @return the femaleThirtyFiveAmongFourtyFour
	 */
	public Integer getFemaleThirtyFiveAmongFourtyFour() {
		return femaleThirtyFiveAmongFourtyFour;
	}
	/**
	 * @param femaleThirtyFiveAmongFourtyFour the femaleThirtyFiveAmongFourtyFour to set
	 */
	public void setFemaleThirtyFiveAmongFourtyFour(
			Integer femaleThirtyFiveAmongFourtyFour) {
		this.femaleThirtyFiveAmongFourtyFour = femaleThirtyFiveAmongFourtyFour;
	}
	/**
	 * @return the femaleFourtyFiveAmongFiftyFour
	 */
	public Integer getFemaleFourtyFiveAmongFiftyFour() {
		return femaleFourtyFiveAmongFiftyFour;
	}
	/**
	 * @param femaleFourtyFiveAmongFiftyFour the femaleFourtyFiveAmongFiftyFour to set
	 */
	public void setFemaleFourtyFiveAmongFiftyFour(
			Integer femaleFourtyFiveAmongFiftyFour) {
		this.femaleFourtyFiveAmongFiftyFour = femaleFourtyFiveAmongFiftyFour;
	}
	/**
	 * @return the femaleFiftyFiveAmongSixtyFour
	 */
	public Integer getFemaleFiftyFiveAmongSixtyFour() {
		return femaleFiftyFiveAmongSixtyFour;
	}
	/**
	 * @param femaleFiftyFiveAmongSixtyFour the femaleFiftyFiveAmongSixtyFour to set
	 */
	public void setFemaleFiftyFiveAmongSixtyFour(
			Integer femaleFiftyFiveAmongSixtyFour) {
		this.femaleFiftyFiveAmongSixtyFour = femaleFiftyFiveAmongSixtyFour;
	}
	/**
	 * @return the femaleSixtyFivePlus
	 */
	public Integer getFemaleSixtyFivePlus() {
		return femaleSixtyFivePlus;
	}
	/**
	 * @param femaleSixtyFivePlus the femaleSixtyFivePlus to set
	 */
	public void setFemaleSixtyFivePlus(Integer femaleSixtyFivePlus) {
		this.femaleSixtyFivePlus = femaleSixtyFivePlus;
	}

}
