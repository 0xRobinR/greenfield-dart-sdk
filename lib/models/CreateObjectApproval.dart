// val ContentLength: Long?
// val ExpectedChecksums: Array<String>?
// val Visibility: VisibilityType?
// val FileType: String?
// val ObjectName: String?
// val BucketName: String?
// val Creator: String?

class CreateObjectEstimate {

  int? contentLength;
  List<dynamic>? expectedChecksums;
  String? visibility;
  String? fileType;
  String? objectName;
  String? bucketName;
  String? creator;

  CreateObjectEstimate({this.contentLength, this.expectedChecksums, this.visibility, this.fileType, this.objectName, this.bucketName, this.creator});

  static fromJson(Map<String, dynamic> json) {
    return CreateObjectEstimate(
      contentLength: json["ContentLength"],
      expectedChecksums: json["ExpectedChecksums"],
      visibility: json["Visibility"],
      fileType: json["FileType"],
      objectName: json["ObjectName"],
      bucketName: json["BucketName"],
      creator: json["Creator"]
    );
  }

  static fromJsonList(List<dynamic> jsonList) {
    return jsonList.map((json) => CreateObjectEstimate.fromJson(json)).toList();
  }

  Map<String, dynamic> toJson() {
    return {
      "ContentLength": contentLength,
      "ExpectedChecksums": expectedChecksums,
      "Visibility": visibility,
      "FileType": fileType,
      "ObjectName": objectName,
      "BucketName": bucketName,
      "Creator": creator
    };
  }

  @override
  String toString() {
    return toJson().toString();
  }

  @override
  bool operator ==(Object other) {
    if (other is CreateObjectEstimate) {
      return contentLength == other.contentLength;
    }
    return false;
  }

  @override
  int get hashCode => contentLength.hashCode;
}