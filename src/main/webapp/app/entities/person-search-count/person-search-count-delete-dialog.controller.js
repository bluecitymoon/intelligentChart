(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PersonSearchCountDeleteController',PersonSearchCountDeleteController);

    PersonSearchCountDeleteController.$inject = ['$uibModalInstance', 'entity', 'PersonSearchCount'];

    function PersonSearchCountDeleteController($uibModalInstance, entity, PersonSearchCount) {
        var vm = this;

        vm.personSearchCount = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PersonSearchCount.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
