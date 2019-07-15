class TrackerLeastBean {
  String autoTime;
  String autoType;
  String connectMode;
  String hit;
  String imei;
  String ringMode;
  String smsLocation;
  String st;
  int enableFnWl;
  int hourTime24;
  ActBean act;
  LctBean lct;
  List<LctsListBean> lcts;

  TrackerLeastBean({this.autoTime, this.autoType, this.connectMode, this.hit, this.imei, this.ringMode, this.smsLocation, this.st, this.enableFnWl, this.hourTime24, this.act, this.lct, this.lcts});

  TrackerLeastBean.fromJson(Map<String, dynamic> json) {    
    this.autoTime = json['auto_time'];
    this.autoType = json['auto_type'];
    this.connectMode = json['connect_mode'];
    this.hit = json['hit'];
    this.imei = json['imei'];
    this.ringMode = json['ring_mode'];
    this.smsLocation = json['sms_location'];
    this.st = json['st'];
    this.enableFnWl = json['enable_fn_wl'];
    this.hourTime24 = json['hourTime24'];
    this.act = json['act'] != null ? ActBean.fromJson(json['act']) : null;
    this.lct = json['lct'] != null ? LctBean.fromJson(json['lct']) : null;
    this.lcts = (json['lcts'] as List)!=null?(json['lcts'] as List).map((i) => LctsListBean.fromJson(i)).toList():null;
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['auto_time'] = this.autoTime;
    data['auto_type'] = this.autoType;
    data['connect_mode'] = this.connectMode;
    data['hit'] = this.hit;
    data['imei'] = this.imei;
    data['ring_mode'] = this.ringMode;
    data['sms_location'] = this.smsLocation;
    data['st'] = this.st;
    data['enable_fn_wl'] = this.enableFnWl;
    data['hourTime24'] = this.hourTime24;
    if (this.act != null) {
      data['act'] = this.act.toJson();
    }
    if (this.lct != null) {
      data['lct'] = this.lct.toJson();
    }
    data['lcts'] = this.lcts != null?this.lcts.map((i) => i.toJson()).toList():null;
    return data;
  }

  @override
  String toString() {
    return 'TrackerLeastBean{autoTime: $autoTime, autoType: $autoType, connectMode: $connectMode, hit: $hit, imei: $imei, ringMode: $ringMode, smsLocation: $smsLocation, st: $st, enableFnWl: $enableFnWl, hourTime24: $hourTime24, act: $act, lct: $lct, lcts: $lcts}';
  }


}

class ActBean {
  String i;
  int p;
  int s;
  int ss;

  ActBean({this.i, this.p, this.s, this.ss});

  ActBean.fromJson(Map<String, dynamic> json) {    
    this.i = json['i'];
    this.p = json['p'];
    this.s = json['s'];
    this.ss = json['ss'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['i'] = this.i;
    data['p'] = this.p;
    data['s'] = this.s;
    data['ss'] = this.ss;
    return data;
  }
}

class LctBean {
  String i;
  String signal;
  String t;
  double o;
  double u;
  int p;
  int wt;

  LctBean({this.i, this.signal, this.t, this.o, this.u, this.p, this.wt});

  LctBean.fromJson(Map<String, dynamic> json) {    
    this.i = json['i'];
    this.signal = json['signal'];
    this.t = json['t'];
    this.o = json['o'];
    this.u = json['u'];
    this.p = json['p'];
    this.wt = json['wt'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['i'] = this.i;
    data['signal'] = this.signal;
    data['t'] = this.t;
    data['o'] = this.o;
    data['u'] = this.u;
    data['p'] = this.p;
    data['wt'] = this.wt;
    return data;
  }
}

class LctsListBean {
  String i;
  String id;
  String radius;
  String t;
  bool isStatic;
  double o;
  double u;
  int p;
  List<WifiListBean> wifi;

  LctsListBean({this.i, this.id, this.radius, this.t, this.isStatic, this.o, this.u, this.p, this.wifi});

  LctsListBean.fromJson(Map<String, dynamic> json) {    
    this.i = json['i'];
    this.id = json['id'];
    this.radius = json['radius'];
    this.t = json['t'];
    this.isStatic = json['isStatic'];
    this.o = json['o'];
    this.u = json['u'];
    this.p = json['p'];
    this.wifi = (json['wifi'] as List)!=null?(json['wifi'] as List).map((i) => WifiListBean.fromJson(i)).toList():null;
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['i'] = this.i;
    data['id'] = this.id;
    data['radius'] = this.radius;
    data['t'] = this.t;
    data['isStatic'] = this.isStatic;
    data['o'] = this.o;
    data['u'] = this.u;
    data['p'] = this.p;
    data['wifi'] = this.wifi != null?this.wifi.map((i) => i.toJson()).toList():null;
    return data;
  }
}

class WifiListBean {
  String i;
  String m;
  String s;

  WifiListBean({this.i, this.m, this.s});

  WifiListBean.fromJson(Map<String, dynamic> json) {    
    this.i = json['i'];
    this.m = json['m'];
    this.s = json['s'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['i'] = this.i;
    data['m'] = this.m;
    data['s'] = this.s;
    return data;
  }
}
