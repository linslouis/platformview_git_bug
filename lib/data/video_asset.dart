class VideoAsset
{
  final String video_path;
  final int video_start_pos;
  final int video_endpos;
  final String video_transition;
  final int transition_duration;
  final String video_effect;

 VideoAsset(this.video_path, this.video_start_pos, this.video_endpos, this.video_transition, this.transition_duration, this.video_effect);

  Map<String,dynamic> toJson(){
    return {
      "video_path": this.video_path,
      "video_start_pos": this.video_start_pos,
      "video_endpos": this.video_endpos,
      "video_transition": this.video_transition,
       "transition_duration": this.transition_duration,
       "video_effect": this.video_effect
    };
  }

}