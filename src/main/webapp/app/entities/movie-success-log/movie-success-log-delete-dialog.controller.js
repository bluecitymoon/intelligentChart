(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('MovieSuccessLogDeleteController',MovieSuccessLogDeleteController);

    MovieSuccessLogDeleteController.$inject = ['$uibModalInstance', 'entity', 'MovieSuccessLog'];

    function MovieSuccessLogDeleteController($uibModalInstance, entity, MovieSuccessLog) {
        var vm = this;

        vm.movieSuccessLog = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            MovieSuccessLog.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
