/*
 * Copyright (c) 2016 Newshunt. All rights reserved.
 */
package me.samen.mesure.headlines;

import java.util.List;

/**
 * @author satosh.dhanyamraju
 */
public class HeadlinesResponse {

  public int code;
  public Data data;

  public static class Langtitles {
    public String en;
    public String translatetitle;
  }

  public static class Sourcefavicon {
    public String url;
    public double width;
    public double height;
  }

  public static class Sourcebrandimage {
    public String url;
    public double width;
    public double height;
  }

  public static class Contentimage {
    public String url;
    public double width;
    public double height;
  }

  public static class Thumbnail {
    public String url;
    public double width;
    public double height;
  }

  public static class Experiment {
    public String topics;
    public String userSegment;
    public String userFeedLogic;
  }

  public static class Story implements Visitable {
    public String type;
    public String id;
    public String title;
    public Langtitles langtitles;
    public String publishtime;
    public String createdtime;
    public String ingestiondate;
    public String sourcefamilykey;
    public String sourcekey;
    public String categorykey;
    public String categoryname;
    public boolean hidedate;
    public String shareurl;
    public String supplementurl;
    public Sourcefavicon sourcefavicon;
    public Sourcebrandimage sourcebrandimage;
    public String sourcenameuni;
    public Contentimage contentImage;
    public Thumbnail thumbnail;
    public String sourcenameenglish;
    public String categorynameenglish;
    public String sourcenameen;
    public String categorynameen;
    public int vieworder;
    public String content;
    public String morecontentloadurl;
    public String backgroundcolor;
    public Experiment experiment;

    @Override
    public int type(TypeFactory typeFactory) {
      return typeFactory.type(this);
    }
  }

  public static class Data {
    public int count;
    public String nextPageUrl;
    public int pageNumber;
    public List<Story> stories;
  }
}
