class FriendsDataBean {
  String imei;
  List<FriendlistListBean> friendlist;

  FriendsDataBean({this.imei, this.friendlist});

  FriendsDataBean.fromJson(Map<String, dynamic> json) {    
    this.imei = json['imei'];
    this.friendlist = (json['friendlist'] as List)!=null?(json['friendlist'] as List).map((i) => FriendlistListBean.fromJson(i)).toList():null;
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['imei'] = this.imei;
    data['friendlist'] = this.friendlist != null?this.friendlist.map((i) => i.toJson()).toList():null;
    return data;
  }

}

class FriendlistListBean {
  String id;
  String imei;
  String name;
  String pic;
  String friendMobile;
  int picId;

  FriendlistListBean({this.id, this.imei, this.name, this.pic, this.friendMobile, this.picId});

  FriendlistListBean.fromJson(Map<String, dynamic> json) {    
    this.id = json['id'];
    this.imei = json['imei'];
    this.name = json['name'];
    this.pic = json['pic'];
    this.friendMobile = json['friendMobile'];
    this.picId = json['picId'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['id'] = this.id;
    data['imei'] = this.imei;
    data['name'] = this.name;
    data['pic'] = this.pic;
    data['friendMobile'] = this.friendMobile;
    data['picId'] = this.picId;
    return data;
  }
}
