<?php /* Smarty version 2.6.26, created on 2015-06-09 14:12:13
         compiled from product.dwt */ ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="<?php echo $this->_tpl_vars['keywords']; ?>
" />
<meta name="description" content="<?php echo $this->_tpl_vars['description']; ?>
" />
<meta name="generator" content="DouPHP v1.1" />
<title><?php echo $this->_tpl_vars['page_title']; ?>
</title>
<link href="http://localhost/wbmh/theme/default/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="http://localhost/wbmh/theme/default/images/jquery.min.js"></script>
<script type="text/javascript" src="http://localhost/wbmh/theme/default/images/global.js"></script>
</head>
<body>
<div id="wrapper">
 <?php $_smarty_tpl_vars = $this->_tpl_vars;
$this->_smarty_include(array('smarty_include_tpl_file' => "inc/header.tpl", 'smarty_include_vars' => array()));
$this->_tpl_vars = $_smarty_tpl_vars;
unset($_smarty_tpl_vars);
 ?>
 <div class="wrap mb">
   <div id="pageLeft">
    <?php $_smarty_tpl_vars = $this->_tpl_vars;
$this->_smarty_include(array('smarty_include_tpl_file' => "inc/product_tree.tpl", 'smarty_include_vars' => array()));
$this->_tpl_vars = $_smarty_tpl_vars;
unset($_smarty_tpl_vars);
 ?>
   </div>
   <div id="pageIn">
     <?php $_smarty_tpl_vars = $this->_tpl_vars;
$this->_smarty_include(array('smarty_include_tpl_file' => "inc/ur_here.tpl", 'smarty_include_vars' => array()));
$this->_tpl_vars = $_smarty_tpl_vars;
unset($_smarty_tpl_vars);
 ?>
     <div id="product">
       <div class="productImg"><a href="<?php echo $this->_tpl_vars['product']['product_image']; ?>
" target="_blank"><img src="<?php echo $this->_tpl_vars['product']['product_image']; ?>
" width="300" /></a></div>
       <div class="productInfo">
         <h1><?php echo $this->_tpl_vars['product']['product_name']; ?>
</h1>
         <ul>
           <li class="productPrice"><?php echo $this->_tpl_vars['lang']['price']; ?>
：<em class="price"><?php echo $this->_tpl_vars['product']['price']; ?>
</em></li>
           <?php $_from = $this->_tpl_vars['defined']; if (!is_array($_from) && !is_object($_from)) { settype($_from, 'array'); }$this->_foreach['defined'] = array('total' => count($_from), 'iteration' => 0);
if ($this->_foreach['defined']['total'] > 0):
    foreach ($_from as $this->_tpl_vars['defined']):
        $this->_foreach['defined']['iteration']++;
?>
           <li><?php echo $this->_tpl_vars['defined']['arr']; ?>
：<?php echo $this->_tpl_vars['defined']['value']; ?>
</li>
           <?php endforeach; endif; unset($_from); ?>
         </ul>
         <dl class="btnAsk">
          <dt><?php echo $this->_tpl_vars['lang']['product_buy']; ?>
：</dt>
          <dd><?php $_from = $this->_tpl_vars['site']['qq']; if (!is_array($_from) && !is_object($_from)) { settype($_from, 'array'); }$this->_foreach['qq'] = array('total' => count($_from), 'iteration' => 0);
if ($this->_foreach['qq']['total'] > 0):
    foreach ($_from as $this->_tpl_vars['qq']):
        $this->_foreach['qq']['iteration']++;
?><?php if (($this->_foreach['qq']['iteration'] <= 1)): ?><a href="http://wpa.qq.com/msgrd?v=3&amp;uin=<?php if (is_array ( $this->_tpl_vars['qq'] )): ?><?php echo $this->_tpl_vars['qq']['number']; ?>
<?php else: ?><?php echo $this->_tpl_vars['qq']; ?>
<?php endif; ?>&amp;site=qq&amp;menu=yes" target="_blank"><img src="http://localhost/wbmh/theme/default/images/online_qq.jpg" /></a><?php endif; ?><?php endforeach; endif; unset($_from); ?> <a href="mailto:<?php echo $this->_tpl_vars['site']['email']; ?>
"><img src="http://localhost/wbmh/theme/default/images/online_email.jpg" /></a></dd>
         </dl>
       </div>
       <div class="clear"></div>
       <div class="productContent">
         <h3><?php echo $this->_tpl_vars['lang']['product_content']; ?>
</h3>
         <ul><?php echo $this->_tpl_vars['product']['content']; ?>
</ul>
       </div>
     </div>
   </div>
   <div class="clear"></div>
 </div>
 <?php $_smarty_tpl_vars = $this->_tpl_vars;
$this->_smarty_include(array('smarty_include_tpl_file' => "inc/online_service.tpl", 'smarty_include_vars' => array()));
$this->_tpl_vars = $_smarty_tpl_vars;
unset($_smarty_tpl_vars);
 ?>
 <?php $_smarty_tpl_vars = $this->_tpl_vars;
$this->_smarty_include(array('smarty_include_tpl_file' => "inc/footer.tpl", 'smarty_include_vars' => array()));
$this->_tpl_vars = $_smarty_tpl_vars;
unset($_smarty_tpl_vars);
 ?> </div>
</body>
</html>