class MessageBean {
  String imei;
  List<ResourceListBean> resource;

  MessageBean({this.imei, this.resource});

  MessageBean.fromJson(Map<String, dynamic> json) {    
    this.imei = json['imei'];
    this.resource = (json['resource'] as List)!=null?(json['resource'] as List).map((i) => ResourceListBean.fromJson(i)).toList():null;
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['imei'] = this.imei;
    data['resource'] = this.resource != null?this.resource.map((i) => i.toJson()).toList():null;
    return data;
  }

}

class ResourceListBean {
  String n;
  String u;
  String s;
  ZshkBean zshk;

  ResourceListBean({this.n, this.u, this.s, this.zshk});

  ResourceListBean.fromJson(Map<String, dynamic> json) {    
    this.n = json['n'];
    this.u = json['u'];
    this.s = json['s'];
    this.zshk = json['zshk'] != null ? ZshkBean.fromJson(json['zshk']) : null;
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['n'] = this.n;
    data['u'] = this.u;
    data['s'] = this.s;
    if (this.zshk != null) {
      data['zshk'] = this.zshk.toJson();
    }
    return data;
  }
}

class ZshkBean {
  String albumId;
  String albumName;
  String imei;
  String userId;
  List<ProgramListListBean> programList;

  ZshkBean({this.albumId, this.albumName, this.imei, this.userId, this.programList});

  ZshkBean.fromJson(Map<String, dynamic> json) {    
    this.albumId = json['albumId'];
    this.albumName = json['albumName'];
    this.imei = json['imei'];
    this.userId = json['userId'];
    this.programList = (json['programList'] as List)!=null?(json['programList'] as List).map((i) => ProgramListListBean.fromJson(i)).toList():null;
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['albumId'] = this.albumId;
    data['albumName'] = this.albumName;
    data['imei'] = this.imei;
    data['userId'] = this.userId;
    data['programList'] = this.programList != null?this.programList.map((i) => i.toJson()).toList():null;
    return data;
  }
}

class ProgramListListBean {
  String programName;
  String programId;

  ProgramListListBean({this.programName, this.programId});

  ProgramListListBean.fromJson(Map<String, dynamic> json) {    
    this.programName = json['programName'];
    this.programId = json['programId'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['programName'] = this.programName;
    data['programId'] = this.programId;
    return data;
  }
}
