class LocHistoryCountBean {
  int count;
  List<ListListData> list;

  LocHistoryCountBean({this.count, this.list});

  LocHistoryCountBean.fromJson(Map<String, dynamic> json) {    
    this.count = json['count'];
    this.list = (json['list'] as List)!=null?(json['list'] as List).map((i) => ListListData.fromJson(i)).toList():null;
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['count'] = this.count;
    data['list'] = this.list != null?this.list.map((i) => i.toJson()).toList():null;
    return data;
  }

}

class ListListData {
  String mapType;
  String time;
  double lat;
  double lon;
  int locateType;
  int power;

  ListListData({this.mapType, this.time, this.lat, this.lon, this.locateType, this.power});

  ListListData.fromJson(Map<String, dynamic> json) {
    this.mapType = json['mapType'];
    this.time = json['time'];
    this.lat = json['lat'];
    this.lon = json['lon'];
    this.locateType = json['locateType'];
    this.power = json['power'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['mapType'] = this.mapType;
    data['time'] = this.time;
    data['lat'] = this.lat;
    data['lon'] = this.lon;
    data['locateType'] = this.locateType;
    data['power'] = this.power;
    return data;
  }
}
