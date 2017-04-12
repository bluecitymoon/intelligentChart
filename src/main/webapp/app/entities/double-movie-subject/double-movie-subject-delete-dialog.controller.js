(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('DoubleMovieSubjectDeleteController',DoubleMovieSubjectDeleteController);

    DoubleMovieSubjectDeleteController.$inject = ['$uibModalInstance', 'entity', 'DoubleMovieSubject'];

    function DoubleMovieSubjectDeleteController($uibModalInstance, entity, DoubleMovieSubject) {
        var vm = this;

        vm.doubleMovieSubject = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            DoubleMovieSubject.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
