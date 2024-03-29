<#include "/inc/layout.ftl" />
<@layout "注册">
<div class="layui-container fly-marginTop">
  <div class="fly-panel fly-panel-user" pad20>
    <div class="layui-tab layui-tab-brief" lay-filter="user">
      <ul class="layui-tab-title">
        <li><a href="/login">登入</a></li>
        <li class="layui-this">注册</li>
      </ul>
      <div class="layui-form layui-tab-content" id="LAY_ucm" style="padding: 20px 0;">
        <div class="layui-tab-item layui-show">
          <div class="layui-form layui-form-pane">
            <form method="post">
              <div class="layui-form-item">
                <label for="L_email" class="layui-form-label">邮箱</label>
                <div class="layui-input-inline">
                  <input type="text" id="L_email" name="email"  lay-verify="email" autocomplete="off" class="layui-input" >
                </div>
                <div class="layui-form-mid layui-word-aux">将会成为您唯一的登入名</div>
              </div>
              <div class="layui-form-item">
                <label for="L_username" class="layui-form-label">昵称</label>
                <div class="layui-input-inline">
                  <input type="text" id="L_username" name="username"  autocomplete="off" class="layui-input" >
                </div>
              </div>
              <div class="layui-form-item">
                <label for="L_pass" class="layui-form-label">密码</label>
                <div class="layui-input-inline">
                  <input type="password" id="L_pass" name="password"  autocomplete="off" class="layui-input" >
                </div>
                <div class="layui-form-mid layui-word-aux">6到16个字符</div>
              </div>
              <div class="layui-form-item">
                <label for="L_repass" class="layui-form-label">确认密码</label>
                <div class="layui-input-inline">
                  <input type="password" id="L_repass" name="repass"  autocomplete="off" class="layui-input" >
                </div>
              </div>
              <div class="layui-form-item">
                <label for="L_vercode" class="layui-form-label">图片验证</label>
                <div class="layui-input-inline">
                  <input type="text" id="L_vercode" name="vercode"  placeholder="请填写验证码" autocomplete="off" class="layui-input">
                </div>
                <div class="">
                  <img id="kaptcha" src="/kaptcha.jpg">
                </div>
              </div>
              <div class="layui-form-item">
                <button class="layui-btn" lay-filter="*" lay-submit alert="true">立即注册</button>
              </div>
              <div class="layui-form-item fly-form-app">
                <span>或者直接使用社交账号快捷注册</span>
                <a href="" onclick="layer.msg('正在通过QQ登入', {icon:16, shade: 0.1, time:0})" class="iconfont icon-qq" title="QQ登入"></a>
                <a href="" onclick="layer.msg('正在通过微博登入', {icon:16, shade: 0.1, time:0})" class="iconfont icon-weibo" title="微博登入"></a>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>

</div>
<script>
  layui.cache.page = 'user';
  $("#kaptcha").click(function (){
    console.log("11111");
    this.src="/kaptcha.jpg";
  });
</script>
</@layout>


