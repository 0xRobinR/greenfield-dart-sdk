class GFBucket {
  String bucketName;
  int created;
  int visibility;
  String createHash;
  int block;

  GFBucket({required this.bucketName, required this.created, required this.visibility, required this.createHash, required this.block});

  static fromJson(Map<String, dynamic> json) {
    return GFBucket(
      bucketName: json["BucketInfo"]["BucketName"],
      created: int.parse(json["BucketInfo"]["CreateAt"]),
      visibility: int.parse(json["BucketInfo"]["Visibility"]),
      createHash: json["CreateTxHash"],
      block: int.parse(json["UpdateAt"])
    );
  }
}