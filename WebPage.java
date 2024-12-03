class WebPage {
  private String url;
  private int pageRank;
  private int searchFrequency;

  public WebPage(String url, int pageRank) {
      this.url = url;
      this.pageRank = pageRank;
      this.searchFrequency = 0;
  }

  public String getUrl() {
      return url;
  }

  public int getPageRank() {
      return pageRank;
  }

  public int getSearchFrequency() {
      return searchFrequency;
  }

  public void incrementSearchFrequency() {
      this.searchFrequency++;
  }

  public int calculatePopularityScore(int pageRankWeight, int searchFrequencyWeight) {
      return (pageRankWeight * pageRank) + (searchFrequencyWeight * searchFrequency);
  }
}
