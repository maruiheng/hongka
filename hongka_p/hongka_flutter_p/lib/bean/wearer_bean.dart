class WearerBean {
  String createdTime;
  String dob;
  String id;
  String imei;
  String lastUpdatedTime;
  String name;
  num gender;
  num height;
  num markPicId;
  num relationship;
  num relationshipPic;
  num weight;
  List<AreasListBean> areas;

  WearerBean({this.createdTime, this.dob, this.id, this.imei, this.lastUpdatedTime, this.name, this.gender, this.height, this.markPicId, this.relationship, this.relationshipPic, this.weight, this.areas});

  WearerBean.fromJson(Map<String, dynamic> json) {    
    this.createdTime = json['createdTime'];
    this.dob = json['dob'];
    this.id = json['id'];
    this.imei = json['imei'];
    this.lastUpdatedTime = json['lastUpdatedTime'];
    this.name = json['name'];
    this.gender = json['gender'];
    this.height = json['height'];
    this.markPicId = json['markPicId'];
    this.relationship = json['relationship'];
    this.relationshipPic = json['relationshipPic'];
    this.weight = json['weight'];
    this.areas = (json['areas'] as List)!=null?(json['areas'] as List).map((i) => AreasListBean.fromJson(i)).toList():null;
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['createdTime'] = this.createdTime;
    data['dob'] = this.dob;
    data['id'] = this.id;
    data['imei'] = this.imei;
    data['lastUpdatedTime'] = this.lastUpdatedTime;
    data['name'] = this.name;
    data['gender'] = this.gender;
    data['height'] = this.height;
    data['markPicId'] = this.markPicId;
    data['relationship'] = this.relationship;
    data['relationshipPic'] = this.relationshipPic;
    data['weight'] = this.weight;
    data['areas'] = this.areas != null?this.areas.map((i) => i.toJson()).toList():null;
    return data;
  }

}

class AreasListBean {
  String lut;
  int type;
  num id;

  AreasListBean({this.lut, this.type, this.id});

  AreasListBean.fromJson(Map<String, dynamic> json) {    
    this.lut = json['lut'];
    this.type = json['type'];
    this.id = json['id'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['lut'] = this.lut;
    data['type'] = this.type;
    data['id'] = this.id;
    return data;
  }
}
