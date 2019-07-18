class ContactListBean {
  String imei;
  List<ListListBean> list;

  ContactListBean({this.imei, this.list});

  ContactListBean.fromJson(Map<String, dynamic> json) {    
    this.imei = json['imei'];
    this.list = (json['list'] as List)!=null?(json['list'] as List).map((i) => ListListBean.fromJson(i)).toList():null;
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['imei'] = this.imei;
    data['list'] = this.list != null?this.list.map((i) => i.toJson()).toList():null;
    return data;
  }

}

class ListListBean {
  String mobile;
  String familyMobile;
  String picId;
  String name;
  String avatar;
  String source;
  String id;
  String friendImei;
  int no;
  int friendId;

  ListListBean({this.mobile, this.familyMobile, this.picId, this.name, this.avatar, this.source, this.id, this.friendImei, this.no, this.friendId});

  ListListBean.fromJson(Map<String, dynamic> json) {    
    this.mobile = json['mobile'];
    this.familyMobile = json['familyMobile'];
    this.picId = json['picId'];
    this.name = json['name'];
    this.avatar = json['avatar'];
    this.source = json['source'];
    this.id = json['id'];
    this.friendImei = json['friendImei'];
    this.no = json['no'];
    this.friendId = json['friendId'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['mobile'] = this.mobile;
    data['familyMobile'] = this.familyMobile;
    data['picId'] = this.picId;
    data['name'] = this.name;
    data['avatar'] = this.avatar;
    data['source'] = this.source;
    data['id'] = this.id;
    data['friendImei'] = this.friendImei;
    data['no'] = this.no;
    data['friendId'] = this.friendId;
    return data;
  }
}
