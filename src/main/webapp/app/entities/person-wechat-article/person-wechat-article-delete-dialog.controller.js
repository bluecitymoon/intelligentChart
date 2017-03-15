(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PersonWechatArticleDeleteController',PersonWechatArticleDeleteController);

    PersonWechatArticleDeleteController.$inject = ['$uibModalInstance', 'entity', 'PersonWechatArticle'];

    function PersonWechatArticleDeleteController($uibModalInstance, entity, PersonWechatArticle) {
        var vm = this;

        vm.personWechatArticle = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PersonWechatArticle.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
