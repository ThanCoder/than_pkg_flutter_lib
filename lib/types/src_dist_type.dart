class SrcDistType {
  String src;
  String dist;
  SrcDistType({
    required this.src,
    required this.dist,
  });

  Map<String, dynamic> toMap() => {
        'src': src,
        'dist': dist,
      };
}
