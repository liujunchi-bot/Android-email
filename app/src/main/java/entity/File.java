package entity;

/*
Qiao Qing
2020/04/04
*/

public class File {

  private String filePath;
  private String fileName;
  private long fileId;

  public File(String filePath, String fileName, long fileId) {
    this.filePath=filePath;
    this.fileName=fileName;
    this.fileId=fileId;
  }

  public String getFilePath() {
    return filePath;
  }

  public void setFilePath(String filePath) {
    this.filePath = filePath;
  }


  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }


  public long getFileId() {
    return fileId;
  }

  public void setFileId(long fileId) {
    this.fileId = fileId;
  }

}
