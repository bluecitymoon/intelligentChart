(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('MovieCategoryDeleteController',MovieCategoryDeleteController);

    MovieCategoryDeleteController.$inject = ['$uibModalInstance', 'entity', 'MovieCategory'];

    function MovieCategoryDeleteController($uibModalInstance, entity, MovieCategory) {
        var vm = this;

        vm.movieCategory = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            MovieCategory.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
