class LocBean {
  String imei;
  String mapType;
  String t;
  String time;
  double lat;
  double lon;
  int power;

  LocBean({this.imei, this.mapType, this.t, this.time, this.lat, this.lon, this.power});

  LocBean.fromJson(Map<String, dynamic> json) {    
    this.imei = json['imei'];
    this.mapType = json['mapType'];
    this.t = json['t'];
    this.time = json['time'];
    this.lat = json['lat'];
    this.lon = json['lon'];
    this.power = json['power'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['imei'] = this.imei;
    data['mapType'] = this.mapType;
    data['t'] = this.t;
    data['time'] = this.time;
    data['lat'] = this.lat;
    data['lon'] = this.lon;
    data['power'] = this.power;
    return data;
  }

  @override
  String toString() {
    return 'LocBean{imei: $imei, mapType: $mapType, t: $t, time: $time, lat: $lat, lon: $lon, power: $power}';
  }

}
