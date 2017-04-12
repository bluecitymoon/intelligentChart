(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('DoubanMovieTagDeleteController',DoubanMovieTagDeleteController);

    DoubanMovieTagDeleteController.$inject = ['$uibModalInstance', 'entity', 'DoubanMovieTag'];

    function DoubanMovieTagDeleteController($uibModalInstance, entity, DoubanMovieTag) {
        var vm = this;

        vm.doubanMovieTag = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            DoubanMovieTag.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
