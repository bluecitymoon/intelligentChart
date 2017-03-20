(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PersonMediaShowUpCountDeleteController',PersonMediaShowUpCountDeleteController);

    PersonMediaShowUpCountDeleteController.$inject = ['$uibModalInstance', 'entity', 'PersonMediaShowUpCount'];

    function PersonMediaShowUpCountDeleteController($uibModalInstance, entity, PersonMediaShowUpCount) {
        var vm = this;

        vm.personMediaShowUpCount = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PersonMediaShowUpCount.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
